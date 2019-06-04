package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import us.feras.mdv.MarkdownView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownInfo);
        markdownView.loadMarkdownFile("https://raw.githubusercontent.com/adrocampos/EEG-Droid/master/markdown/epilepsy.md");
    }
}
