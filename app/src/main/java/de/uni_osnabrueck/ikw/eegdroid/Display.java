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


    private void loadData (File file) {

        lineDataSets = new LineDataSet[nChannels];

        ArrayList<float[]> data_rows = new ArrayList<>();
        try {
            // create csvReader object and skip first 3 Lines
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(3).build();

            String[] values;
            while ((values = csvReader.readNext()) != null) {

                float[] intFloats = new float[values.length];
                for (int i=0; i < values.length-1; i++) {
                    intFloats[i] = Float.parseFloat(values[i]);
                }
                data_rows.add(intFloats);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        for (int j=0; j < nChannels; j++) {
            ArrayList<Entry> data_temp = new ArrayList<>();

            for (int k=0; k < data_rows.size(); k++){
                data_temp.add(new Entry(data_rows.get(k)[0], data_rows.get(k)[j]  ));
            }

            String nameLineDataSet = "Channel " + Integer.toString(j);
            lineDataSets[j] = new LineDataSet(data_temp, nameLineDataSet);
        }

        Log.d("LineDataSets[] LEN", Integer.toString(lineDataSets.length));
        Log.d("LineDataSet entries", Integer.toString(lineDataSets[0].getEntryCount()));
        Log.d("DataRow len", Integer.toString(data_rows.size()));


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