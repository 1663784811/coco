package com.cyyaw.webrtc.rtc.peer;

import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.SessionDescription;

/**
 * 同伴事件 回调
 */
public interface PeerCallBack {

    /**
     * 发送数据包
     */
    void onSendIceCandidate(String userId, IceCandidate candidate);

    /**
     * 发送数据包
     */
    void onSendOffer(String userId, SessionDescription description);

    /**
     * 发送数据包
     */
    void onSendAnswer(String userId, SessionDescription description);

    /**
     * 添加远程流
     */
    void onRemoteStream(String userId, MediaStream stream);

    /**
     * 移除过程流
     */
    void onRemoveStream(String userId, MediaStream stream);

    /**
     * 断开连接
     */
    void onDisconnected(String userId);
}