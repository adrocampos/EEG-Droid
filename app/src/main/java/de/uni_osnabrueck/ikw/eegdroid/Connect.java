package de.uni_osnabrueck.ikw.eegdroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Connect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button scan_button = findViewById(R.id.scan_button);

        scan_button.setOnClickListener(v -> startActivity(new Intent(Connect.this, DeviceScanActivity.class)));
    }

}
