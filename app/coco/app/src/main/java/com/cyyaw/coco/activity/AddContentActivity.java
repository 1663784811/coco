package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cyyaw.coco.R;

public class AddContentActivity extends AppCompatActivity {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AddContentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
    }
}