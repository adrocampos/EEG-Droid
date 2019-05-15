package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
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
import java.util.Timer;
import java.util.TimerTask;

public class Display extends AppCompatActivity {

    private ArrayList<File> arrayListOfFiles;
    private String[] arrayOfNames;
    private File fileToPlot;
    private LineChart chart;
    private LineDataSet[] lineDataSets;
    private int nChannels = 8;
    private Timer timer;
    private int fps = 30;
    private Float max_in_X;


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


        OnChartValueSelectedListener ol = new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight h) {
                //entry.getData() returns null here
            }

            @Override
            public void onNothingSelected() {

            }
        };

        chart = findViewById(R.id.chart);
        chart.setOnChartValueSelectedListener(ol);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
//        chart.setHighlightPerDragEnabled(true);
        chart.setPinchZoom(true);
//        chart.setBackgroundColor(Color.LTGRAY);
//
//        seekBarX.setProgress(30);
//
//        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
//        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
////        l.setYOffset(11f);
//

//
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.GRAY);
        //leftAxis.setAxisMaximum(30f);
        //leftAxis.setAxisMinimum(-30f);
        leftAxis.setLabelCount(13, true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.WHITE);

//
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
//        //rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);

        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setLabelCount(5, true);
        bottomAxis.setValueFormatter(new MyXAxisValueFormatter());
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setGridColor(Color.WHITE);
        bottomAxis.setTextColor(Color.GRAY);



        LimitLine current = new LimitLine(0, "here");
        current.setLineColor(Color.BLUE);
        current.setLineWidth(2f);

        bottomAxis.addLimitLine(current);
//        //xAxis.setTypeface(tfLight);
//        xAxis.setTextSize(11f);
//        xAxis.setTextColor(Color.WHITE);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);



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
                        Log.d("finish", "finish");


                    }
                });
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        class UpdateChart extends TimerTask {
            float position_in_x= 0;
            public void run() {
                position_in_x = position_in_x + 1000 / fps;
                if (position_in_x > max_in_X){
                    timer.cancel();
                    timer.purge();
                }
                chart.moveViewToX(position_in_x);
                Log.d("positionX", Float.toString(position_in_x));
            }
        }


        switch (item.getItemId()) {

            case R.id.play:
                timer = new Timer();
                chart.moveViewToX(0);
                TimerTask updateChart = new UpdateChart();
                timer.schedule(updateChart, 1000, 1000 / fps);
                chart.moveViewToX(lineDataSets[0].getXMax());
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
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

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        int[] colors = {ContextCompat.getColor(getApplicationContext(), R.color.aqua),
                ContextCompat.getColor(getApplicationContext(), R.color.fuchsia),
                ContextCompat.getColor(getApplicationContext(), R.color.green),
                ContextCompat.getColor(getApplicationContext(), android.R.color.holo_purple),
                ContextCompat.getColor(getApplicationContext(), R.color.orange),
                ContextCompat.getColor(getApplicationContext(), R.color.red),
                ContextCompat.getColor(getApplicationContext(), R.color.yellow),
                ContextCompat.getColor(getApplicationContext(), R.color.black)
        };

        for (int i=0; i < lineDataSets.length ; i++) {
            lineDataSets[i].setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSets[i].setColor(colors[i]);
            lineDataSets[i].setValueTextColor(colors[i]);
            lineDataSets[i].setDrawCircles(false);
            lineDataSets[i].setLineWidth(1f);
            lineDataSets[i].setVisible(true);
            dataSets.add(lineDataSets[i]);
        }

        chart.setData(new LineData(dataSets));
        chart.invalidate();
        chart.setVisibleXRangeMaximum(1000); //Shows a maximum of 1 sec per screen
        max_in_X = lineDataSets[0].getXMax();
    }





}