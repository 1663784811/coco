package com.cyyaw.webrtc.net;

/**
 * 网络事件  接收数据
 */
public interface SocketReceiveDataEvent {

    /**
     * 接收 webrtc
     */
    void onReceiveWebRtc(String message);

    /**
     * 接收聊天数据
     */
    void onReceiveChat(String fromId, String toId,String message);

    /**
     * 网络连接成功回调
     */
    void onOpenCallBack();


    void logout(String str);

    void onConnectError();
}
