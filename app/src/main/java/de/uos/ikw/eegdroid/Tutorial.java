package de.uni_osnabrueck.ikw.eegdroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class Tutorial extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.webViewTutorial);
        webView.getSettings().setJavaScriptEnabled(true);

        if (isNetworkAvailable()) {
            webView.loadUrl("https://adrocampos.github.io/EEG-Droid/tutorial.html");
        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.no_internet))
                    .setMessage(getString(R.string.no_internet_message));

            alert.setPositiveButton(getString(R.string.access_offline), (dialog, which) -> webView.loadUrl("file:///android_asset/docs/tutorial.html"));
            alert.show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return Objects.requireNonNull(connectivityManager.getActiveNetworkInfo()).isConnected();
    }
}
