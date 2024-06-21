package com.cyyaw.webrtc.rtc;

/**
 * 类型
 */
public class EnumType {

    /**
     * 电话状态
     */
    public enum CallState {
        Idle,
        Outgoing,
        Incoming,
        Connecting,
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
