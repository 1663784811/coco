package com.cyyaw.webrtc.rtc.aaaa.webrtc.peer;

import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.SessionDescription;

/**
 * 同伴事件 回调
 */
public interface PeerEvent {

    void onSendIceCandidate(String userId, IceCandidate candidate);

    void onSendOffer(String userId, SessionDescription description);

    void onSendAnswer(String userId, SessionDescription description);

    void onRemoteStream(String userId, MediaStream stream);

    /**
     * 移除过程流
     */
    void onRemoveStream(String userId, MediaStream stream);

    void onDisconnected(String userId);
}