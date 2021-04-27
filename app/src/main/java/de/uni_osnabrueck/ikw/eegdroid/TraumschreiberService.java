package de.uni_osnabrueck.ikw.eegdroid;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;



public class TraumschreiberService {

    public final static String DEVICE_NAME = "traumschreiber";

    // UUIDS
    public final UUID serviceUUID = UUID.fromString("00000ee6-0000-1000-8000-00805f9b34fb");
    public final ArrayList<String> notifyingUUIDs = new ArrayList<String>() {
        {
            add("0000ee60-0000-1000-8000-00805f9b34fb");
            add("0000ee61-0000-1000-8000-00805f9b34fb");
            add("0000ee62-0000-1000-8000-00805f9b34fb");
        }
    };
    public final UUID notifyUUID = UUID.fromString("0000ee60-0000-1000-8000-00805f9b34fb");
    public final UUID configUUID = UUID.fromString("0000ecc0-0000-1000-8000-00805f9b34fb");
    public final UUID codeUUID = UUID.fromString("0000c0de-0000-1000-8000-00805f9b34fb");

    private final static String TAG = "TraumschreiberService";
    public  String mTraumschreiberDeviceAddress;
    public static final int nChannels = 24;
    private static final int[] decodedSignal = new int[24];
    public static int[] signalBitShift = new int[24];
    private static int[] signalOffset = new int[24];
    private static int pkgCount;
    private static boolean header = true;

    public TraumschreiberService() {}

    public static boolean isTraumschreiberDevice(String bluetoothDeviceName) {
        return bluetoothDeviceName.toLowerCase().contains(DEVICE_NAME);
    }

    public static boolean isNewModel(String bluetoothDeviceName) {
        return bluetoothDeviceName.startsWith("T");
    }

    public void initiateCentering(){
        signalOffset = new int[24];
        pkgCount = 0;
    }

    /***
     * Decodes all kinds of data packages received via bluetooth from a Traumschreiber:
     *  # Signal Data - Units of voltage (distributed over the 3 notifying characteristics)
     *  # Encoding Updates
     *  # Config Data
     * @param dataBytes      raw bytes received from the Traumschreiber over bluetooth
     * @return channelData, encodingData, configData
     */
    public static int[] decode(byte[] dataBytes, String characteristicId) {

        /* Decode Channel Values */
        if (characteristicId.equals("60")){
            if (header) {
                return decodeDpcm(Arrays.copyOfRange(dataBytes, 1, dataBytes.length));
            } else {
                return decodeDpcm(dataBytes);
            }

        /* Decode Channel Encodings Factors */
        } else if (characteristicId.equals("de")) {
                Log.d(TAG, "RECEIVED FROM C0DE, RAW: " + Arrays.toString(dataBytes));
                // Iterate through the 12 received bytes and split them into unsigned nibbles
                for (int i = 0; i < 12; i++) {
                    signalBitShift[i * 2] = (dataBytes[i] >> 4) & 0xf;
                    signalBitShift[i * 2 + 1] = dataBytes[i] & 0xf;
                }
                //Log.d(TAG, "RECEIVED FROM C0DE: " + Arrays.toString(signalBitShift));
                int[] encodingData = new int[]{0xc0de, signalBitShift[1], dataBytes[13]}; //
                return encodingData;

        /* Decode Config Data */
        } else if (characteristicId.equals("c0")) {
                Log.d(TAG, "RECEIVED FROM CONFIG: " + Arrays.toString(dataBytes));
                int[] configData = new int[dataBytes.length];
                for (int i = 0; i < dataBytes.length; i++) {
                    configData[i] = (int) dataBytes[i];
                }
                return configData;

        } else {
            return null;
        }
    }

    /***
     * Converts bytes to ints and adds the values of the current data to the previous data.
     * @param  deltaBytes
     * @return int[] data
     */
    public static int[] decodeDpcm(byte[] deltaBytes) {
        //Log.v(TAG, "Encoded Delta: " + Arrays.toString(deltaBytes));
        int[] delta = bytesTo16bitInts(deltaBytes);
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
     * Turns an array of bytes into an array of 10bit ints.
     * @param bytes
     * @return int[] data
     */
    public static int[] bytesTo10bitInts(byte[] bytes) {
        // Number of ints : bytes*8/10 (8bits per byte and 10bits per int)
        int[] data = new int[bytes.length * 8 / 10];
        /*
         * Pattern repeats after 5 bytes. Therefore we process the bytes in chunks of 5.
         * Processing 5 bytes yields 4 (10bit) ints.
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
            if (data[i] >= Math.pow(2,9)) data[i] -= Math.pow(2,10);
        }
        return data;
    }

    /***
     * Turns an array of bytes into an array of 14bit ints.
     * @param bytes
     * @return int[] data
     */
    public static int[] bytesTo14bitInts(byte[] bytes) {
        // Number of ints : bytes*8/14 (8bits per byte and 14bits per int)
        int[] data = new int[nChannels];
        /*
         * Decoding pattern repeats after 7 Bytes or 4 14-bit-ints (=56bit).
         * Therefore, we process the data in chunks of 4.
         */
        int idx = 0;
        for (int i = 0; i <= data.length - 4; i += 4) {
            idx = i * 7/4; //adjusted after every loop step : 7 after 1, 14 after 2, 21 after 3, etc.

            data[i + 0] = ((bytes[idx+0] & 0xff) << 6)  | ((bytes[idx+1] & 0xfc) >> 2);
            data[i + 1] = ((bytes[idx+1] & 0x03) << 12) | ((bytes[idx+2] & 0xff) << 4) | ((bytes[idx+3] & 0xf0) >> 4);
            data[i + 2] = ((bytes[idx+3] & 0x0f) << 10) | ((bytes[idx+4] & 0xff) << 2) | ((bytes[idx+5] & 0xc0) >> 6);
            data[i + 3] = ((bytes[idx+5] & 0x3f) << 8)  | ((bytes[idx+6] & 0xff) >> 0);
        }
        // Subtracting 2^14 turns unsigned 14bit ints into their 2's complement
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= Math.pow(2,13)) data[i] -= Math.pow(2,14);
        }
        return data;
    }
    /***
     * Turns an array of bytes into an array of 16bit ints.
     * @param bytes
     * @return int[] data
     */
    public static int[] bytesTo16bitInts(byte[] bytes) {
        // Number of ints : bytes*8/14 (8bits per byte and 14bits per int)
        int[] data = new int[nChannels];
        for (int i = 0; i < nChannels; i++) {
            data[i] = ((bytes[i*2]&0xff) << 8) | (bytes[i*2+1] & 0xff);
        }
        // Subtracting 2^16 turns unsigned 16bit ints into their 2's complement
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= Math.pow(2,15)) data[i] -= Math.pow(2,16);
        }
        return data;
    }
}