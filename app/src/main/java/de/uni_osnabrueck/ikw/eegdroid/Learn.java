package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Learn extends AppCompatActivity {

    private WebView webView;
    private File webView_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webViewLearn);
        webView.getSettings().setJavaScriptEnabled(true);

        //File to save the webView
        webView_file = new File(getFilesDir().getPath() + "/learnWebView.tmp");

        // If connection: load newest info
        if (isNetworkAvailable()==true) {
            webView.loadUrl("https://adrocampos.github.io/EEG-Droid/epilepsy.html");

       // If no connection: read last webView
        } else {
//            try {
//                FileInputStream fis = new FileInputStream(webView_file);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                webView = (WebView) ois.readObject();
//                ois.close();
//            } catch (FileNotFoundException e) {
//                Log.d("onCreate", e.getMessage());
//            } catch (Exception e) {
//                Log.d("onCreate", e.getMessage());
//            }
            webView.loadUrl("file:///android_asset/docs/epilepsy.html");
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            FileOutputStream fos = new FileOutputStream(webView_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(webView);
            oos.close();
        } catch (FileNotFoundException e) {
            Log.d("onDestroy", e.getMessage());
        } catch (Exception e) {
            Log.d("onDestroy", e.getMessage());

        }
    }
}
