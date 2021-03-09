package de.uni_osnabrueck.ikw.eegdroid;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;



public class TraumschreiberService {

    public final static String DEVICE_NAME = "traumschreiber";

    private final ArrayList<String> notifyingUUIDs = new ArrayList<String>() {
        {
            add("0000ee60-0000-1000-8000-00805f9b34fb");
            add("0000ee61-0000-1000-8000-00805f9b34fb");
            add("0000ee62-0000-1000-8000-00805f9b34fb");
        }
    };
    private final String configCharacteristicUuid = "0000ecc0-0000-1000-8000-00805f9b34fb";
    private final String codeCharacteristicUuid = "0000c0de-0000-1000-8000-00805f9b34fb";
    private final static String TAG = "TraumschreiberService";
    public static String mTraumschreiberDeviceAddress;
    private static final byte[] dpcmBuffer = new byte[30];
    private static final byte[] dpcmBuffer2 = new byte[30];
    private static final int[] decodedSignal = new int[24];
    public static int[] signalBitShift = new int[24];
    private static int[] signalOffset = new int[24];
    private static int pkgCount;
    private static boolean characteristic0Ready = false;


    public TraumschreiberService() {

    }


    public TraumschreiberService(String traumschreiberDeviceAddress) {
        this.mTraumschreiberDeviceAddress = traumschreiberDeviceAddress;
    }

    public static boolean isTraumschreiberDevice(String bluetoothDeviceName) {
        return bluetoothDeviceName.toLowerCase().contains(DEVICE_NAME);
    }

    public static boolean isNewModel(String bluetoothDeviceName) {
        return bluetoothDeviceName.startsWith("T");
    }

    public static void setSignalScaling(int[] newShift) {
        signalBitShift = newShift;
    }

    public static void initiateCentering(){
        signalOffset = new int[24];
        pkgCount = 0;
    }

    /***
     * Decodes all kinds of data packages received via bluetooth from a Traumschreiber:
     *  # Signal Data - Units of voltage (distributed over the 3 notifying characteristics)
     *  # Encoding Updates
     *  # Config Data
     * @param dataBytes
     * @return int[] intsToReturn of the datapoint values as integers
     */
    public static int[] decode(byte[] dataBytes, boolean newModel, String characteristicId) {

        int[] intsToReturn;

        // NOTE TO SELF FOR DEBUGGING: WRITING ON THE DPCM BUFFER IS OK! NO NEED FOR FURTHER CHECKS
        // Characteristic 1
        if (characteristicId.equals("60")) {
            System.arraycopy(dataBytes, 0, dpcmBuffer, 0, 20);
            //Log.v(TAG, "DataBytes 0: " + Arrays.toString(dataBytes));
            characteristic0Ready = true;
            intsToReturn = null;
            return intsToReturn;

            // Characteristic 2
        } else if (characteristic0Ready && characteristicId.equals("61")) {
            System.arraycopy(dataBytes, 0, dpcmBuffer, 20, 10);
            System.arraycopy(dataBytes, 10, dpcmBuffer2, 0, 10);
            intsToReturn = decodeDpcm(dpcmBuffer);
            //Log.v(TAG, "DataBytes 1: " + Arrays.toString(dataBytes));
            //Log.v(TAG, "dpcmBuffer 1:  " + Arrays.toString(dpcmBuffer));

            // Characteristic 3
        } else if (characteristic0Ready && characteristicId.equals("62")) {
            System.arraycopy(dataBytes, 0, dpcmBuffer2, 10, 20);
            intsToReturn = decodeDpcm(dpcmBuffer2);
            //Log.v(TAG, "DataBytes 2 " + Arrays.toString(dataBytes));
            //Log.v(TAG, "dpcmBuffer 2:  " + Arrays.toString(dpcmBuffer2));

            // Characteristic c0de - Updates Codebook
        } else if (characteristicId.equals("de")){
            Log.d(TAG, "RECEIVED FROM C0DE, RAW: " + Arrays.toString(dataBytes));
            // Iterate through the 12 received bytes and split them into unsigned nibbles
            for(int i = 0; i < 12; i++){
                signalBitShift[i*2] = (dataBytes[i]>>4) & 0xf;
                signalBitShift[i*2+1] = dataBytes[i] & 0xf;
            }

            //Log.d(TAG, "RECEIVED FROM C0DE Characteritistic!" + Arrays.toString(signalBitShift));
            intsToReturn = new int[] {0xc0de, signalBitShift[1],dataBytes[13]}; // just an arbitrary flag for the next handler, since normal values <512
            return intsToReturn;

        } else if (characteristicId.equals("c0")){
            Log.d(TAG, "RECEIVED FROM Config Characteritistic!" + Arrays.toString(dataBytes));
            int[] configData = new int[dataBytes.length];
            for(int i = 0; i < dataBytes.length; i++){
                configData[i] = (int) dataBytes[i];
            }
            return configData;

        } else {
            intsToReturn = null;
            return intsToReturn;
        }

        return intsToReturn;

    }

    /***
     * Converts bytes to ints and adds the values of the current data to the previous data.
     * @param  deltaBytes
     * @return int[] data
     */
    public static int[] decodeDpcm(byte[] deltaBytes) {
        //Log.v(TAG, "Encoded Delta: " + Arrays.toString(deltaBytes));
        int[] delta = bytesTo10bitInts(deltaBytes);
        //Log.v(TAG, "Decoded Delta: " + Arrays.toString(delta));

        for (int i = 0; i < 24; i++) {
            decodedSignal[i] += (delta[i] << signalBitShift[i]);
            // Centering the Signal 
            if (pkgCount < 200) signalOffset[i] += 0.005 * decodedSignal[i]; // moving average over 200 pkgs
            if (pkgCount == 200) decodedSignal[i] -= signalOffset[i];
        }
        pkgCount++;
        return decodedSignal;
    }

    /***
     * Turns an array of bytes into an array fo 10bit ints.
     * @param bytes
     * @return int[] data
     */
    public static int[] bytesTo10bitInts(byte[] bytes) {
        // Number of ints : bytes*8/10 (8bits per byte and 10bits per int)
        int[] data = new int[bytes.length * 8 / 10];

        /**
         * Pattern repeats after 5 bytes. Therefore we process the bytes in chunks of 5.
         * Processing 5 bytes yields 4 (10bit) ints.
         * The index 'idx' of the resulting int array 'data' has to be adjusted at every loop step
         * to account for the gap between the indices resulting from the 5/4 byte-to-int ratio
         */
        int idx = 0;
        for (int i = 0; i <= bytes.length - 5; i += 5) {
            idx = i * 4 / 5;
            data[idx + 0] = ((bytes[i + 0] & 0xff) << 2) | ((bytes[i + 1] & 0xc0) >>> 6);
            data[idx + 1] = ((bytes[i + 1] & 0x3f) << 4) | ((bytes[i + 2] & 0xf0) >>> 4);
            data[idx + 2] = ((bytes[i + 2] & 0x0f) << 6) | ((bytes[i + 3] & 0xfc) >>> 2);
            data[idx + 3] = ((bytes[i + 3] & 0x03) << 8) | ((bytes[i + 4] & 0xff) >>> 0);
        }
        // Subtracting 1024 turns unsigned 10bit ints into their 2's complement
        for (int i = 0; i < data.length; i++) { 
            if (data[i] > 511) data[i] -= 1024;
        }

        return data;
    }
}