package com.cyyaw.webrtc.rtc.engine;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * 框架回调
 */
public interface EngineCallback {


    /**
     * 加入房间成功
     */
    void joinRoomSucc();

    /**
     * 退出房间成功
     */
    void exitRoom();

    void onSendIceCandidate(String userId, IceCandidate candidate);

    void onSendOffer(String userId, SessionDescription description);

    void onSendAnswer(String userId, SessionDescription description);

    void onRemoteStream(String userId);

    /**
     * 连接断开
     */
    void onDisconnected(String userId);

}
