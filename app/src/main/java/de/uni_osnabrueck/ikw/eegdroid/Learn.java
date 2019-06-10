package de.uni_osnabrueck.ikw.eegdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import us.feras.mdv.MarkdownView;

public class Learn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownLearn);
        markdownView.loadMarkdownFile("https://raw.githubusercontent.com/adrocampos/EEG-Droid/master/learning/epilepsy.md");
    }
}
