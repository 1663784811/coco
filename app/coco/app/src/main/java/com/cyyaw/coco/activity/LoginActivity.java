package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.LoginDao;


/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText passwordInput = findViewById(R.id.passwordInput);


        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener((View v) -> {
            String userName = phoneInput.getText().toString();
            String password = passwordInput.getText().toString();

            LoginDao.userLogin(LoginActivity.this, userName, password);
        });
    }


}

