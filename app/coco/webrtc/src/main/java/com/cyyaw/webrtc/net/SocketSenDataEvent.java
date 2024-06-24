package com.cyyaw.webrtc.net;

/**
 * 发送数据
 */
public interface SocketSenDataEvent {

    /**
     * 创建房间
     */
    void sendCreateRoom(String room, int roomSize);


}
