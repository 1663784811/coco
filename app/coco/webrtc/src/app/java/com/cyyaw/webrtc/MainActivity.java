package com.cyyaw.webrtc;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener((View v) -> {


            VideoActivity.openActivity(MainActivity.this, "11", true, "22", false, false);

        });

    }
}

