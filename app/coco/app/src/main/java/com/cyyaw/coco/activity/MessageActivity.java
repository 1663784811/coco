package com.cyyaw.coco.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.webrtc.VideoActivity;

public class MessageActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        View videoBtn = findViewById(R.id.videoBtn);
        videoBtn.setOnClickListener((View v) -> {
            MyApplication.toast("sssssssssss");
            VideoActivity.openActivity(MessageActivity.this, "sss", false, "aaaaaaaaaaaa", false, false);
        });


    }

    @Override
    public Activity getActivity() {
        return this;
    }
}