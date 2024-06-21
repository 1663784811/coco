package com.cyyaw.webrtc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener((View v) -> {


            Intent intent = new Intent(MainActivity.this, VideoActivity.class);


            startActivity(intent);

        });

    }
}

