package com.cyyaw.webrtc.net;

import java.util.List;

/**
 * 发送数据
 */
public interface SocketSenDataEvent {

    /**
     * 创建房间
     */
    void sendAskRoom(int roomSize);


    /**
     * 发送邀请
     */
    void sendInvite(String room, List<String> users, boolean audioOnly);


    /**
     * 发送离开房间
     */
    void sendLeave(String room, String userId);


    /**
     * 发送我已经响铃
     */
    void sendRingBack(String targetId, String room);


    /**
     * 发送拒绝
     */
    void sendRefuse(String room, String inviteId, int refuseType);


    /**
     * 发送取消
     */
    void sendCancel(String mRoomId, List<String> userIds);


    /**
     * 发送加入房间
     */
    void sendJoin(String room);

    /**
     * 切换到语音接听
     */
    void sendTransAudio(String userId);

    /**
     * 发送关闭连接
     */
    void sendDisconnect(String room, String userId);

    /**
     * 数据包
     */
    void sendOffer(String userId, String sdp);

    /**
     * 数据包
     */
    void sendAnswer(String userId, String sdp);

    /**
     * 数据包
     */
    void sendIceCandidate(String userId, String id, int label, String candidate);


}
