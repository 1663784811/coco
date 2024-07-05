package com.cyyaw.webrtc.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.fragment.MediaOperationCallback;
import com.cyyaw.webrtc.fragment.multicall.FragmentMeeting;
import com.cyyaw.webrtc.permission.Permissions;
import com.cyyaw.webrtc.rtc.CallEngineKit;
import com.cyyaw.webrtc.rtc.session.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSession;
import com.cyyaw.webrtc.rtc.session.CallSessionCallback;


import java.util.UUID;

/**
 * 多人通话界面
 */
public class RoomCallActivity extends AppCompatActivity implements CallSessionCallback, MediaOperationCallback {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private ImageView meetingHangupImageView;
    private CallSessionCallback currentFragment;
    public static final String EXTRA_MO = "isOutGoing";
    private boolean isOutgoing;


    public static void openActivity(Activity activity, String room, boolean isOutgoing) {
        Intent intent = new Intent(activity, RoomCallActivity.class);
        intent.putExtra("room", room);
        intent.putExtra(EXTRA_MO, isOutgoing);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());
        setContentView(R.layout.activity_room_call);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        meetingHangupImageView = findViewById(R.id.meetingHangupImageView);
        Fragment fragment = new FragmentMeeting(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.meeting_container, fragment).commit();
        currentFragment = (CallSessionCallback) fragment;
    }

    private void initListener() {
        meetingHangupImageView.setOnClickListener((View v) -> {
            handleHangup();
        });
    }

    private void initData() {
        Intent intent = getIntent();
        String room = intent.getStringExtra("room");
        isOutgoing = intent.getBooleanExtra(EXTRA_MO, false);

        String[] per = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
        Permissions.request(this, per, integer -> {
            if (integer == 0) {
                // 权限同意
                init(room, isOutgoing);
            } else {
                // 权限拒绝
                this.finish();
            }
        });

    }

    private void init(String room, boolean isOutgoing) {

        if (isOutgoing) {
            // 创建一个房间并进入
            CallEngineKit.Instance().createAndJoinRoom(this, "room-" + UUID.randomUUID().toString().substring(0, 16));
        } else {
            // 加入房间
            CallEngineKit.Instance().joinRoom(getApplicationContext(), room);
        }
        CallSession session = CallEngineKit.Instance().getCurrentSession();
        if (session == null) {
            this.finish();
        } else {
            session.setSessionCallback(this);
        }
    }


    @TargetApi(19)
    private static int getSystemUiVisibility() {
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        return flags;
    }


    //-------------------------------------------------回调相关------------------------------------
    @Override
    public void didCallEndWithReason(EnumType.CallEndReason var1) {
        finish();
    }

    @Override
    public void didChangeState(EnumType.CallState callState) {
        handler.post(() -> currentFragment.didChangeState(callState));
    }

    @Override
    public void didChangeMode(boolean var1) {
        handler.post(() -> currentFragment.didChangeMode(var1));
    }

    @Override
    public void didCreateLocalVideoTrack() {
        handler.post(() -> currentFragment.didCreateLocalVideoTrack());
    }

    @Override
    public void didReceiveRemoteVideoTrack(String userId) {
        handler.post(() -> currentFragment.didReceiveRemoteVideoTrack(userId));
    }

    @Override
    public void didUserLeave(String userId) {
        handler.post(() -> currentFragment.didUserLeave(userId));
    }

    @Override
    public void didError(String var1) {
        finish();
    }

    @Override
    public void didDisconnected(String userId) {

    }


    // 处理挂断事件
    private void handleHangup() {
        CallEngineKit.Instance().leaveRoom();
        this.finish();
    }

    @Override
    public void showFloatingView() {

    }

    @Override
    public void switchAudio() {

    }
}
