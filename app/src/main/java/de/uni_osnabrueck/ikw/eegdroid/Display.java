package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Display extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private ArrayList<File> arrayListOfFiles;
    private String[] arrayOfNames;
    private File fileToPlot;
    List<Entry[]> data;

    private LineChart chart;
    private SeekBar seekBarX;
    private TextView tvX;

    private LineDataSet[] lineDataSets;
    private int nChannels = 8;


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

        tvX = findViewById(R.id.tvX);
        seekBarX = findViewById(R.id.seekBar);

        chart = findViewById(R.id.chart);
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.LTGRAY);




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

    //Fills lineDataSets with the content of CSV files
    private void loadData (File file) {

        lineDataSets = new LineDataSet[nChannels];

        try {
            // create csvReader object and skip first 3 Lines
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(3).build();

            List<String[]> values_strings = csvReader.readAll();

            //Ignore the first column (time) and the last one (empty)
            for (int j=1; j < values_strings.get(0).length-1; j++) {
                ArrayList<Entry> arrayOfEntry = new ArrayList<>();
                for (int k=0; k < values_strings.size(); k++){
                    arrayOfEntry.add(new Entry(Float.parseFloat(values_strings.get(k)[0]), Float.parseFloat(values_strings.get(k)[j])));
                }
                String nameLineDataSet = "Channel " + j;
                lineDataSets[j-1] = new LineDataSet(arrayOfEntry, nameLineDataSet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void plot (){

    }


    private void setData(List<Float[]> data) {


    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        Log.i("Entry selected", e.toString());
//
//        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
//                .getAxisDependency(), 500);
        //chart.zoomAndCenterAnimated(2.5f, 2.5f, e.getX(), e.getY(), chart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
        //chart.zoomAndCenterAnimated(1.8f, 1.8f, e.getX(), e.getY(), chart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText(String.valueOf(seekBarX.getProgress()));
//
//        setData(seekBarX.getProgress(), seekBarY.getProgress());
//
//        // redraw
//        chart.invalidate();
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


}