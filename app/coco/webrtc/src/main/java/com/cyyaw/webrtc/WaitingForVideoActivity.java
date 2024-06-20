package com.cyyaw.webrtc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WaitingForVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 添加Activity到堆栈
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
    }


}
