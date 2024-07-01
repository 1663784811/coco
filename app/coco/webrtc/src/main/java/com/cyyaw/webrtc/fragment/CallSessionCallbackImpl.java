package com.cyyaw.webrtc.fragment;

import android.os.Handler;
import android.os.Looper;

import com.cyyaw.webrtc.VideoActivity;
import com.cyyaw.webrtc.rtc.engine.EnumType;
import com.cyyaw.webrtc.rtc.session.CallSessionCallback;

public class CallSessionCallbackImpl implements CallSessionCallback {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private SingleCallFragment currentFragment;

    private VideoActivity videoActivity;

    public CallSessionCallbackImpl(SingleCallFragment currentFragment, VideoActivity videoActivity) {
        this.currentFragment = currentFragment;
        this.videoActivity = videoActivity;
    }


    @Override
    public void didCallEndWithReason(EnumType.CallEndReason reason) {
        handler.post(() -> currentFragment.didCallEndWithReason(reason));
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
        handler.post(() -> currentFragment.didError(var1));
    }

    @Override
    public void didDisconnected(String userId) {
        handler.post(() -> currentFragment.didDisconnected(userId));
    }


}
