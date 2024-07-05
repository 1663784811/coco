package com.cyyaw.webrtc.rtc.engine;


import android.view.View;

import java.util.List;

/**
 * rtc基类
 */
public interface Engine {

    /**
     * 初始化
     */
    void init(EngineCallback callback);

    /**
     * 加入房間
     */
    void joinRoom(List<String> userIds);

    /**
     * 有人进入房间
     */
    void userIn(String userId);

    /**
     * 离开房间
     */
    void leaveRoom(String userId);

    /**
     * receive Offer
     */
    void receiveOffer(String userId, String description);

    /**
     * receive Answer
     */
    void receiveAnswer(String userId, String sdp);

    /**
     * receive IceCandidate
     */
    void receiveIceCandidate(String userId, String id, int label, String candidate);

    /**
     * 开启本地预览
     */
    View setupLocalPreview(boolean isOverlay);

    /**
     * 关闭本地预览
     */
    void stopPreview();

    /**
     * 开始远端预览
     */
    View setupRemoteVideo(String userId, boolean isO);

    /**
     * 切换摄像头
     */
    void switchCamera();

    /**
     * 设置静音
     */
    boolean muteAudio(boolean enable);

    /**
     * 开启扬声器
     */
    boolean toggleSpeaker(boolean enable);

    /**
     * 切换外放和耳机
     */
    boolean toggleHeadset(boolean isHeadset);

    /**
     * 释放所有内容
     */
    void release();

}
