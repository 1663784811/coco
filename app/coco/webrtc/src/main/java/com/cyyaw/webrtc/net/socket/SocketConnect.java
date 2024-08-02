package com.cyyaw.webrtc.net.socket;

/**
 * 网络
 */
public interface SocketConnect {

    /**
     * 发送webrtc数据
     */
    void sendWebrtcData(String to, String msg);

    /**
     * 发送聊天消息
     */
    void sendChatData(String to, String msg);
}
