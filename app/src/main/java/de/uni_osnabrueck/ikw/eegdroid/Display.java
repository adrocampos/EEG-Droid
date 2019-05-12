package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Display extends AppCompatActivity {

    private ArrayList<File> arrayListOfFiles;
    private String[] arrayOfNames;
    private File fileToPlot;
    List<float[]> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ManageSessions manager = new ManageSessions();
        arrayListOfFiles = manager.getArrayListOfFiles();

        //Create the list of names for display in dialog
        arrayOfNames = new String[arrayListOfFiles.size()];
        for(int i=0; i < arrayOfNames.length; i++) {
            arrayOfNames[i] = arrayListOfFiles.get(i).getName();
        }

        //Dialog for choosing the session to plot
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(R.string.select_recording)
                .setItems(arrayOfNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("test", arrayListOfFiles.get(which).getName()); // Here we access to the file
                        fileToPlot = arrayListOfFiles.get(which);
                        loadData(fileToPlot);
                        dialog.dismiss();
                        //plot the data


                    }
                });
        alert.show();



    }





    private void loadData (File file) {

        data = new ArrayList<float[]>();

        try {
            // create csvReader object and skip first 3 Lines
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(3).build();

            String[] values;
            while ((values = csvReader.readNext()) != null) {

                float[] intFloats = new float[values.length];
                for (int i=0; i < values.length-1; i++) {
                    intFloats[i] = Float.parseFloat(values[i]);
                }
                data.add(intFloats);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void plot (){

    }




}