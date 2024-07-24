package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cyyaw.coco.R;

public class QrCodeActivity extends AppCompatActivity {

    private static final String TAG = QrCodeActivity.class.getName();

    private static final String USERID = "userId";


    public static void openActivity(Context context, String userId) {
        Intent intent = new Intent(context, QrCodeActivity.class);
        intent.putExtra(USERID, userId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
    }









}