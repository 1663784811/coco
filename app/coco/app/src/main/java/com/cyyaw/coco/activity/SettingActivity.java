package com.cyyaw.coco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;

public class SettingActivity extends AppCompatActivity {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.logOutBtn).setOnClickListener((View v) -> {
            // 清除token
            MyApplication.saveToken("");
            // 跳转登录页面
            LoginActivity.openActivity(SettingActivity.this);
            finish();
        });


    }


}