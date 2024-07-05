package com.cyyaw.webrtc.rtc.session;


import com.cyyaw.webrtc.rtc.EnumType;

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

    /**
     * 模式改变
     */
    void didChangeMode(boolean isAudioOnly);

    /**
     * 创建本地
     */
    void didCreateLocalVideoTrack();

    /**
     * 远程画面
     */
    void didReceiveRemoteVideoTrack(String userId);

    /**
     * 用户离开
     */
    void didUserLeave(String userId);

    /**
     * 错误
     */
    void didError(String error);

    /**
     * 连接断开
     */
    void didDisconnected(String userId);

}
