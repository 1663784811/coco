package com.cyyaw.coco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.coco.R;
import com.cyyaw.coco.utils.ActivityUtils;


/**
 * 个人主页
 */
public class PersonCenterActivity extends AppCompatActivity {

    private static final String USERID = "userId";
    private static final String USERNAME = "userName";


    public static void openActivity(Context context, String userId, String userName) {
        Intent intent = new Intent(context, PersonCenterActivity.class);
        intent.putExtra(USERID, userId);
        intent.putExtra(USERNAME, userName);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        Intent intent = getIntent();
        String userId = intent.getStringExtra(USERID);
        String userName = intent.getStringExtra(USERNAME);


        Button privateMessageBtn = findViewById(R.id.privateMessageBtn);

        privateMessageBtn.setOnClickListener((View v) -> {
            ActivityUtils.startActivity(PersonCenterActivity.this, MessageActivity.class, null);
        });
    }


}