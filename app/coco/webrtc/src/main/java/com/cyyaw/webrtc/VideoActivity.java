package com.cyyaw.webrtc;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.cyyaw.webrtc.fragment.FragmentAudio;
import com.cyyaw.webrtc.fragment.FragmentVideo;
import com.cyyaw.webrtc.fragment.SingleCallFragment;
import com.cyyaw.webrtc.permission.Permissions;
import com.cyyaw.webrtc.rtc.EnumType;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.session.CallSession;

import java.util.UUID;


/**
 * 等页面, 语音, 视频
 */
public class VideoActivity extends AppCompatActivity implements CallSession.CallSessionCallback {

    private SkyEngineKit gEngineKit;

    public boolean isAudioOnly;
    private boolean isOutgoing;

    private String targetId;

    private SingleCallFragment currentFragment;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 添加Activity到堆栈
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_call);
        gEngineKit = SkyEngineKit.Instance();
        Intent intent = getIntent();
        isAudioOnly = intent.getBooleanExtra("audioOnly", false);
        String[] per = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
        Permissions.request(this, per, integer -> {
            if (integer == 0) {
                // 权限同意
                init("111", true, isAudioOnly, false);
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void init(String targetId, boolean outgoing, boolean audioOnly, boolean isReplace) {
        if (audioOnly) {
            // 语音
            currentFragment = new FragmentAudio();
        } else {
            // 视频
            currentFragment = new FragmentVideo();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isReplace) {
            fragmentManager.beginTransaction().replace(android.R.id.content, currentFragment).commit();
        } else {
            fragmentManager.beginTransaction().add(android.R.id.content, currentFragment).commit();
        }
        if (outgoing && !isReplace) {
            // 创建会话
          String  room = UUID.randomUUID().toString() + System.currentTimeMillis();
            boolean b = gEngineKit.startOutCall(getApplicationContext(), room, targetId, audioOnly);
            if (!b) {
                finish();
                return;
            }
//            App.getInstance().setRoomId(room);
//            App.getInstance().setOtherUserId(targetId);
            CallSession session = gEngineKit.getCurrentSession();
            if (session == null) {
                finish();
            } else {
                session.setSessionCallback(this);
            }
        } else {
            CallSession session = gEngineKit.getCurrentSession();
            if (session == null) {
                finish();
            } else {
                if (session.isAudioOnly() && !audioOnly) { //这种情况是，对方切换成音频的时候，activity还没启动，这里启动后需要切换一下
                    isAudioOnly = session.isAudioOnly();
                    currentFragment.didChangeMode(true);
                }
                session.setSessionCallback(this);
            }
        }


    }

    //        String room = UUID.randomUUID().toString() + System.currentTimeMillis();
    //  SkyEngineKit.Instance().startOutCall(this, room, "ssss", false);


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(headsetPlugReceiver);  //注销监听
        handler.removeCallbacksAndMessages(null);
    }

    public boolean isFromFloatingView() {




        return false;
    }

    public boolean isOutgoing() {
        return isOutgoing;
    }

    public SkyEngineKit getEngineKit() {
        return gEngineKit;
    }

    public void showFloatingView() {
//        if (!WindHandle.checkOverlayPermission(this)) {
//            return;
//        }
//        Intent intent = new Intent(this, FloatingVoipService.class);
//        intent.putExtra(EXTRA_TARGET, targetId);
//        intent.putExtra(EXTRA_AUDIO_ONLY, isAudioOnly);
//        intent.putExtra(EXTRA_MO, isOutgoing);
//        startService(intent);
//        finish();
    }

    // 切换到语音通话
    public void switchAudio() {
        init(targetId, isOutgoing, true, true);
    }



    // ======================================界面回调================================
    @Override
    public void didCallEndWithReason(EnumType.CallEndReason reason) {
//        App.getInstance().setOtherUserId("0");
        //交给fragment去finish
//        finish();
        handler.post(() -> currentFragment.didCallEndWithReason(reason));
    }

    @Override
    public void didChangeState(EnumType.CallState callState) {
        if (callState == EnumType.CallState.Connected) {
            isOutgoing = false;
        }
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
        handler.post(() -> currentFragment.didError(var1));
    }

    @Override
    public void didDisconnected(String userId) {
        handler.post(() -> currentFragment.didDisconnected(userId));
    }

}
