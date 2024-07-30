package com.cyyaw.webrtc.net.socket;

import java.util.List;

/**
 * 网络
 */
public interface SocketConnect {

    /**
     * 创建房间
     */
    void sendAskRoom(int roomSize, String myId);

    /**
     * 邀请通话
     * @param room 房间号
     */
    void sendInvite(String room, String myId, List<String> users, boolean audioOnly);

    void sendLeave(String myId, String room, String targetId);

    /**
     * 发送我已经响铃
     */
    void sendRing(String myId, String targetId, String room);

    void sendRefuse(String room, String targetId, String myId, int refuseType);

    void sendCancel(String mRoomId, String myId, List<String> userIds);

    /**
     * 发送加入房间
     */
    void sendJoin(String room, String myId);

    void sendOffer(String myId, String targetId, String sdp);

    void sendAnswer(String myId, String targetId, String sdp);

    void sendIceCandidate(String myId, String targetId, String id, int label, String candidate);

    void sendTransAudio(String myId, String targetId);

    void sendDisconnect(String room, String myId, String targetId);

    void sendChatMsg(String sendUserId, String toUserid, String data);
}
