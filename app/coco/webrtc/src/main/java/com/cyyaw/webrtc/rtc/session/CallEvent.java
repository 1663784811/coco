package com.cyyaw.webrtc.rtc.session;

import java.util.List;


public interface CallEvent {

    /**
     * 申请创建房间
     */
    void sendAskRoom(int roomSize);

    /**
     * 发送单人邀请
     */
    void sendInvite(String room, List<String> userIds, boolean audioOnly);

    /**
     * 发送拒绝
     */
    void sendRefuse(String room, String inviteId, int refuseType);

    /**
     *
     */
    void sendTransAudio(String toId);

    /**
     * 断开连接
     */
    void sendDisConnect(String room, String toId, boolean isCrashed);

    /**
     * 发送取消
     */
    void sendCancel(String mRoomId, List<String> toId);

    /**
     * 加入房间
     */
    void sendJoin(String room);

    /**
     * 发送响铃回复
     */
    void sendRingBack(String targetId, String room);

    /**
     * 发送离开
     */
    void sendLeave(String room, String userId);

    /**
     * 发送提供包
     */
    void sendOffer(String userId, String sdp);

    /**
     * 发送回答包
     */
    void sendAnswer(String userId, String sdp);

    /**
     * 发送ICE 数据包
     */
    void sendIceCandidate(String userId, String id, int label, String candidate);


    /**
     * 开始响铃
     */
    void shouldStartRing(boolean isComing);

    /**
     * 关闭响铃
     */
    void shouldStopRing();


}
