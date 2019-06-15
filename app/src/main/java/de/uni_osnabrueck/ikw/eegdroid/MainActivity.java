package de.uni_osnabrueck.ikw.eegdroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.flatbuffers.Table;

import java.io.File;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static File dirSessions;
    private ManageSessions ManageSessions = new ManageSessions();
    private String nameDir = "/sessions_EEG";
    private Uri dirUri;
    private TextView appName;
    private TextView appVersion;
    private ApplicationInfo applicationInfo;
    private TableRow tableRowRecord;
    private TableRow tableRowDisplay;
    private TableRow tableRowManage;
    private TableRow tableRowLearn;
    private TableRow tableRowTutorial;
    private TableRow tableRowEpibot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // File object to save the directory to save the EEG recordings
        dirSessions = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + nameDir);
        ManageSessions.createDirectory(dirSessions);
        dirUri = Uri.parse(Environment.getExternalStorageDirectory() + "/sessions_EEG/"); //Uri to open the folder with sessions //ToDo Make relative
        Log.d("Main", dirUri.getPath());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appName = (TextView) findViewById(R.id.main_app_name);
        appVersion = (TextView) findViewById(R.id.main_app_version);

        applicationInfo = getApplicationInfo();
        appName.setText(applicationInfo.loadLabel(getPackageManager()));
        appVersion.setText(BuildConfig.VERSION_NAME);

        tableRowRecord = (TableRow) findViewById(R.id.tableRowRecord);
        tableRowDisplay = (TableRow) findViewById(R.id.tableRowDisplay);
        tableRowManage = (TableRow) findViewById(R.id.tableRowManage);
        tableRowLearn = (TableRow) findViewById(R.id.tableRowLearn);
        tableRowTutorial = (TableRow) findViewById(R.id.tableRowTutorial);
        tableRowEpibot = (TableRow) findViewById(R.id.tableRowEpibot);

        tableRowRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Record.class);
                startActivity(intent);
            }
        });

        tableRowDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Display.class);
                intent.putExtra("dirString", dirSessions.getPath());
                startActivity(intent);
            }
        });

        tableRowManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageSessions.class);
                intent.putExtra("dirString", dirSessions.getPath());
                startActivity(intent);
            }
        });


        tableRowLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Learn.class);
                startActivity(intent);
            }
        });

        tableRowTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Tutorial.class);
                startActivity(intent);
            }
        });

        tableRowEpibot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Epibot.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.record) {
            Intent intent = new Intent(this, Record.class);
            startActivity(intent);

        } else if (id == R.id.display) {
            Intent intent = new Intent(this, Display.class);
            intent.putExtra("dirString", dirSessions.getPath());
            startActivity(intent);

        } else if (id == R.id.manage) {
            Intent intent = new Intent(this, ManageSessions.class);
            intent.putExtra("dirString", dirSessions.getPath());
            startActivity(intent);

        } else if (id == R.id.tfanalysis) {
            Intent intent = new Intent(this, TFAnalysis.class);
            startActivity(intent);

        } else if (id == R.id.learn) {
            Intent intent = new Intent(this, Learn.class);
            startActivity(intent);

        } else if (id == R.id.tutorial) {
            Intent intent = new Intent(this, Tutorial.class);
            startActivity(intent);

        } else if (id == R.id.epibot) {
            Intent intent = new Intent(this, Epibot.class);
            startActivity(intent);

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.info) {
            Intent intent = new Intent(this, Info.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static File getDirSessions() {
        return dirSessions;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}