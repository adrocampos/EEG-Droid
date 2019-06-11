package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class Learn extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        webView = (WebView) findViewById(R.id.webViewLearn);
        webView.loadUrl("https://raw.githubusercontent.com/adrocampos/EEG-Droid/master/learning/epilepsy.html");
    }
}
