package com.cyyaw.webrtc.net;

import java.util.List;

/**
 * 发送数据
 */
public interface SocketSenDataEvent {

    /**
     * 创建房间
     */
    void sendCreateRoom(String room, int roomSize);


    void sendInvite(String room, List<String> users, boolean audioOnly);


}
