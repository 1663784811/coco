package com.cyyaw.coco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.cyyaw.coco.activity.MainActivity;

public class IndexActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        MyApplication.post(() -> {
            MainActivity.openActivity(IndexActivity.this);
            finish();
        }, 100);


    }
}