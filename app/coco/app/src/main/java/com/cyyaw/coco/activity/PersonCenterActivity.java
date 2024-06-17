package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cyyaw.coco.R;
import com.cyyaw.coco.utils.ActivityUtils;


/**
 * 个人主页
 */
public class PersonCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);


        Button privateMessageBtn = findViewById(R.id.privateMessageBtn);

        privateMessageBtn.setOnClickListener((View v) -> {
            ActivityUtils.startActivity(PersonCenterActivity.this, MessageActivity.class, null);
        });
    }
}