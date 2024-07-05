package com.cyyaw.webrtc.net;

/**
 * 网络事件  接收数据
 */
public interface SocketReceiveDataEvent {

    /**
     * 网络连接成功回调
     */
    void onOpenCallBack();

    /**
     * 登录成功
     */
    void loginSuccess(String userId, String avatar);


    void logout(String str);


    /**
     * 接收邀请数据
     */
    void onReceiveInvite(String room, boolean audioOnly, String inviteId, String userList);


    void onCancel(String inviteId);

    /**
     * 接收到对方已响铃
     */
    void onReceiveRing(String userId);

    /**
     * 接收所有人信息
     */
    void onReceivePeers(String room, String userList, int roomSize);

    void onNewPeer(String myId);

    void onReject(String userId, int type);

    /**
     *
     */
    void onOffer(String userId, String sdp);

    // onAnswer
    void onAnswer(String userId, String sdp);

    // ice-candidate
    void onIceCandidate(String userId, String id, int label, String candidate);

    void onLeave(String userId);

    void onTransAudio(String userId);

    void onDisConnect(String userId);

    void onConnectError();
}
