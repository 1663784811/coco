package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;


/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener((View v) -> {
            MyApplication.saveToken("sssssssss");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }


}

