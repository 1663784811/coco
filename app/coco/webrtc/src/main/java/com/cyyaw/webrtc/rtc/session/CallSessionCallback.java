package com.cyyaw.webrtc.rtc.session;


import com.cyyaw.webrtc.rtc.engine.EnumType;


/**
 * 回调
 */
public interface CallSessionCallback {

    /**
     * call 结束原因
     */
    void didCallEndWithReason(EnumType.CallEndReason var1);

    /**
     * 状态改变
     */
    void didChangeState(EnumType.CallState var1);

    void didChangeMode(boolean isAudioOnly);

    void didCreateLocalVideoTrack();

    void didReceiveRemoteVideoTrack(String userId);

    void didUserLeave(String userId);

    void didError(String error);

    void didDisconnected(String userId);

}
