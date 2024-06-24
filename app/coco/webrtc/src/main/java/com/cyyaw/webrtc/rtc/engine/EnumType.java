package com.cyyaw.webrtc.rtc.engine;

/**
 * 类型
 */
public class EnumType {

    /**
     * 电话状态
     */
    public enum CallState {
        //  初始
        Idle,
        // 呼出
        Outgoing,
        // 呼入
        Incoming,
        // 连接
        Connecting,
        // 已连接
        Connected;

        CallState() {
        }
    }


    public enum CallEndReason {
        Busy,
        SignalError,
        RemoteSignalError,
        Hangup,
        MediaError,
        RemoteHangup,
        OpenCameraFailure,
        Timeout,
        AcceptByOtherClient;

        CallEndReason() {
        }
    }

    public enum RefuseType {
        Busy,
        Hangup,
    }


}
