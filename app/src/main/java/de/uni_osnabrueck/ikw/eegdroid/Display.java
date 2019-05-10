package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


public class Display extends AppCompatActivity {


    private ArrayList<File> arrayListOfFiles;
    private String[] arrayOfNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ManageSessions manager = new ManageSessions();
        arrayListOfFiles = manager.getArrayListOfFiles();

        arrayOfNames = new String[arrayListOfFiles.size()];
        for(int i=0; i < arrayOfNames.length; i++) {
            arrayOfNames[i] = arrayListOfFiles.get(i).getName();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(R.string.select_recording)
                .setItems(arrayOfNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Log.d("test", arrayListOfFiles.get(which).getName()); // Here we access to the file
                        dialog.dismiss();
                        //Log.d("Display", Integer.toString(which));
                    }
                });
        alert.show();
    }

    private void plot(File file){

    }



}