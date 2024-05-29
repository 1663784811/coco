package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}