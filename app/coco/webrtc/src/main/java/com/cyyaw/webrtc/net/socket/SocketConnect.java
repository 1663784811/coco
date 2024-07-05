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
     * @param myId
     * @param users
     * @param audioOnly
     */
    void sendInvite(String room, String myId, List<String> users, boolean audioOnly);

    void sendLeave(String myId, String room, String userId);

    void sendRing(String myId, String targetId, String room);

    void sendRefuse(String room, String inviteId, String myId, int refuseType);

    void sendCancel(String mRoomId, String myId, List<String> userIds);

    void sendJoin(String room, String myId);

    void sendOffer(String myId, String userId, String sdp);

    void sendAnswer(String myId, String userId, String sdp);

    void sendIceCandidate(String myId, String userId, String id, int label, String candidate);

    void sendTransAudio(String myId, String userId);

    void sendDisconnect(String room, String myId, String userId);

}
