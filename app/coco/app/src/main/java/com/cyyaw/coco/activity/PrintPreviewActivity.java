package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cyyaw.coco.R;

public class PrintPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);


        View nowPrintBtn = findViewById(R.id.nowPrintBtn);






        nowPrintBtn.setOnClickListener((View v) -> {
            // 第一步: 获取打印像素数据

            // 第二步: 发送数据


        });
    }






}

