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

    /**
     * 原因
     */
    public enum CallEndReason {
        Busy("对方忙线中"),
        SignalError("连接断开"),
        RemoteSignalError("对方网络断开"),
        Hangup("挂断"),
        MediaError("媒体错误"),
        RemoteHangup("对方挂断"),
        OpenCameraFailure("打开摄像头错误"),
        Timeout("对方未接听"),
        AcceptByOtherClient("在其它设备接听");

        private String note;

        CallEndReason(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }
    }

    public enum RefuseType {
        Busy,
        Hangup,
    }


}
