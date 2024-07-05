package com.cyyaw.webrtc;


/**
 * 状态回调
 */
public interface StatusCallBack {

    /**
     * 网络状态回调
     */
    void netWorkStatus(NetStatus netStatus);


    enum NetStatus {
        ERROR, // 错误
        CONNECT,// 开启
        CLOSE,// 关闭
    }

}
