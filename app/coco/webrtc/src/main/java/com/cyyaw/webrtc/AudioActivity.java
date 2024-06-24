package com.cyyaw.webrtc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AudioActivity extends AppCompatActivity {

    public static final String EXTRA_TARGET = "targetId";
    public static final String EXTRA_MO = "isOutGoing";
    public static final String EXTRA_USER_NAME = "userName";
    public static final String EXTRA_FROM_FLOATING_VIEW = "fromFloatingView";


    public static void openActivity(Context context, String targetId, boolean isOutgoing, String inviteUserName, boolean isClearTop) {
        Intent intent = new Intent(context, AudioActivity.class);
        intent.putExtra(EXTRA_MO, isOutgoing);
        intent.putExtra(EXTRA_TARGET, targetId);
        intent.putExtra(EXTRA_USER_NAME, inviteUserName);
        intent.putExtra(EXTRA_FROM_FLOATING_VIEW, false);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
    }


}
