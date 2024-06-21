package com.cyyaw.webrtc.socket;

/**
 *
 */
public interface IEvent {


    /**
     * 打开websocket
     */
    void onOpen();

    /**
     * 登录成功
     */
    void loginSuccess(String userId, String avatar);


    /**
     * 邀请
     */
    void onInvite(String room, boolean audioOnly, String inviteId, String userList);

    /**
     * 取消
     */
    void onCancel(String inviteId);

    void onRing(String userId);


    void onPeers(String myId, String userList, int roomSize);

    void onNewPeer(String myId);

    void onReject(String userId, int type);

    // onOffer
    void onOffer(String userId, String sdp);

    // onAnswer
    void onAnswer(String userId, String sdp);

    // ice-candidate
    void onIceCandidate(String userId, String id, int label, String candidate);

    void onLeave(String userId);

    void logout(String str);

    void onTransAudio(String userId);

    void onDisConnect(String userId);

    void reConnect();

}
