package com.cyyaw.webrtc.fragment;


/**
 * 媒体操作回调
 */
public interface MediaOperationCallback {

    /**
     * 显示小视窗
     */
    void showFloatingView();


    /**
     * 切换到语音通话
     */
    void switchAudio();

    /**
     *
     */
    boolean isFromFloatingView();


    boolean isOutgoing();


    void setIsOutgoing(boolean b);


    void setAudioOnly(boolean b);



    void finish();

}
