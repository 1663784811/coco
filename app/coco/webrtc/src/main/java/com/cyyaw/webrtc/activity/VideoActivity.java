package com.cyyaw.webrtc.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cyyaw.webrtc.R;
import com.cyyaw.webrtc.fragment.CallSessionCallbackImpl;
import com.cyyaw.webrtc.fragment.singlecall.FragmentAudio;
import com.cyyaw.webrtc.fragment.singlecall.FragmentVideo;
import com.cyyaw.webrtc.fragment.MediaOperationCallback;
import com.cyyaw.webrtc.fragment.singlecall.SingleCallFragment;
import com.cyyaw.webrtc.utils.WindHandle;
import com.cyyaw.webrtc.permission.Permissions;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.session.CallSession;

import java.util.UUID;


/**
 * 视频音频通话
 */
public class VideoActivity extends AppCompatActivity implements MediaOperationCallback {

    public static final String EXTRA_TARGET = "targetId";
    public static final String EXTRA_MO = "isOutGoing";
    public static final String EXTRA_AUDIO_ONLY = "audioOnly";
    public static final String EXTRA_USER_NAME = "userName";
    public static final String EXTRA_FROM_FLOATING_VIEW = "fromFloatingView";
    private static final String TAG = "CallSingleActivity";

    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isOutgoing;
    private String targetId;
    public boolean isAudioOnly;
    private boolean isFromFloatingView;
    private SingleCallFragment currentFragment;
    private String room;

    public static Intent getCallIntent(Context context, String targetId, boolean isOutgoing, String inviteUserName, boolean isAudioOnly, boolean isClearTop) {
        Intent voip = new Intent(context, VideoActivity.class);
        voip.putExtra(EXTRA_MO, isOutgoing);
        voip.putExtra(EXTRA_TARGET, targetId);
        voip.putExtra(EXTRA_USER_NAME, inviteUserName);
        voip.putExtra(EXTRA_AUDIO_ONLY, isAudioOnly);
        voip.putExtra(EXTRA_FROM_FLOATING_VIEW, false);
        if (isClearTop) {
            voip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return voip;
    }


    /**
     * 打开Activity
     */
    public static void openActivity(Context context, String targetId, boolean isOutgoing, String inviteUserName, boolean isAudioOnly, boolean isClearTop) {
        Intent intent = getCallIntent(context, targetId, isOutgoing, inviteUserName, isAudioOnly, isClearTop);
        if (context instanceof Activity) {
            context.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindHandle.setStatusBarOrScreenStatus(this);
        setContentView(R.layout.activity_single_call);
        final Intent intent = getIntent();
        targetId = intent.getStringExtra(EXTRA_TARGET);
        isFromFloatingView = intent.getBooleanExtra(EXTRA_FROM_FLOATING_VIEW, false);
        isOutgoing = intent.getBooleanExtra(EXTRA_MO, false);
        isAudioOnly = intent.getBooleanExtra(EXTRA_AUDIO_ONLY, false);

        if (isFromFloatingView) {
//            Intent serviceIntent = new Intent(this, FloatingVoipService.class);
//            stopService(serviceIntent);
//            init(targetId, false, isAudioOnly, false);
        } else {
            // 权限检测
            String[] per;
            if (isAudioOnly) {
                per = new String[]{android.Manifest.permission.RECORD_AUDIO};
            } else {
                per = new String[]{android.Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
            }
            Permissions.request(this, per, integer -> {
                Log.d(TAG, "Permissions.request integer = " + integer);
                if (integer == 0) {
                    // 权限同意
                    init(targetId, isOutgoing, isAudioOnly, false);
                } else {
                    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                    // 权限拒绝
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初化
     *
     * @param targetId  目标ID
     * @param outgoing  是否呼出
     * @param audioOnly 只语音
     * @param isReplace 是否重新进入
     */
    private void init(String targetId, boolean outgoing, boolean audioOnly, boolean isReplace) {
        if (audioOnly) {
            // 语音
            currentFragment = new FragmentAudio(this, isOutgoing);
        } else {
            // 视频
            currentFragment = new FragmentVideo(this, isOutgoing, isFromFloatingView);
        }
        if (isReplace) {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, currentFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, currentFragment).commit();
        }
        if (outgoing && !isReplace) {
            // 创建会话
            room = UUID.randomUUID().toString() + System.currentTimeMillis();
            boolean b = SkyEngineKit.Instance().startOutCall(getApplicationContext(), room, targetId, audioOnly);
            if (!b) {
                finish();
                return;
            }
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            // 设置回调
            session.setSessionCallback(new CallSessionCallbackImpl(currentFragment));
        } else {
            CallSession session = SkyEngineKit.Instance().getCurrentSession();
            if (session.isAudioOnly() && !audioOnly) {
                //这种情况是，对方切换成音频的时候，activity还没启动，这里启动后需要切换一下
                isAudioOnly = session.isAudioOnly();
                currentFragment.didChangeMode(true);
            }
            session.setSessionCallback(new CallSessionCallbackImpl(currentFragment));
        }

    }


    // 显示小窗
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

    /**
     * 切换到语音通话
     */
    public void switchAudio() {
        this.isAudioOnly = true;
        init(targetId, isOutgoing, true, true);
    }

}