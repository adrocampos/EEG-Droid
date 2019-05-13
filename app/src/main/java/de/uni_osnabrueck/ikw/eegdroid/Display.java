package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
//        chart.setOnChartValueSelectedListener(this);
//        chart.getDescription().setEnabled(false);
//        chart.setTouchEnabled(true);
//        chart.setDragDecelerationFrictionCoef(0.9f);
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setDrawGridBackground(false);
//        chart.setHighlightPerDragEnabled(true);
//        chart.setPinchZoom(true);
//        chart.setBackgroundColor(Color.LTGRAY);

        seekBarX.setProgress(30);

        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart.getXAxis();
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        //rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);





        //Dialog for choosing the session to plot
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(R.string.select_recording)
                .setItems(arrayOfNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("test", arrayListOfFiles.get(which).getName()); // Here we access to the file
                        fileToPlot = arrayListOfFiles.get(which);
                        loadData(fileToPlot);
                        setData();
                        dialog.dismiss();
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        Log.d("finish", "finisg");


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
                String nameLineDataSet = "Channel_" + j;
                lineDataSets[j-1] = new LineDataSet(arrayOfEntry, nameLineDataSet);
            }

            csvReader.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setData (){

        lineDataSets[0].setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSets[1].setAxisDependency(YAxis.AxisDependency.LEFT);

        Log.d("lineDataSets[0]", Integer.toString(lineDataSets[0].getEntryCount()));
        Log.d("lineDataSets[0]", Float.toString(lineDataSets[0].getValues().get(5).getX()) + "_"+  Float.toString(lineDataSets[0].getValues().get(5).getY() ) );

        // create a data object with the data sets

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        dataSets.add(lineDataSets[0]);
        dataSets.add(lineDataSets[1]);

        LineData data2 = new LineData(dataSets);


        chart.setData(data2);
        chart.invalidate();


    }


    private void setData(List<Float[]> data) {


    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));

//        setData(seekBarX.getProgress(), seekBarY.getProgress());

        // redraw
        chart.invalidate();
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