package com.cyyaw.webrtc.rtc.session;

import java.util.List;


public interface ISkyEvent {

    /**
     * 创建房间
     */
    void createRoom(String room, int roomSize);

    /**
     * 发送单人邀请
     */
    void sendInvite(String room, List<String> userIds, boolean audioOnly);

    /**
     *
     */
    void sendRefuse(String room, String inviteId, int refuseType);

    /**
     *
     */
    void sendTransAudio(String toId);

    void sendDisConnect(String room, String toId, boolean isCrashed);

    void sendCancel(String mRoomId, List<String> toId);

    /**
     * 加入房间
     */
    void sendJoin(String room);

    void sendRingBack(String targetId, String room);

    void sendLeave(String room, String userId);

    // sendOffer
    void sendOffer(String userId, String sdp);

    // sendAnswer
    void sendAnswer(String userId, String sdp);

    // sendIceCandidate
    void sendIceCandidate(String userId, String id, int label, String candidate);

    /**
     * 远程响铃
     */
    void onRemoteRing();

    /**
     * 开始响铃
     */
    void shouldStartRing(boolean isComing);

    /**
     * 关闭响铃
     */
    void shouldStopRing();


}
