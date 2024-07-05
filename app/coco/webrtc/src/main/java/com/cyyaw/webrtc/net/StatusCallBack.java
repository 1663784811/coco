package com.cyyaw.webrtc.net;


/**
 * 状态回调
 */
public interface StatusCallBack {

    /**
     * 网络状态回调
     */
    void netWorkStatus(NetStatus netStatus, String msg);


    enum NetStatus {
        ERROR, // 错误
        CONNECT,// 开启
        CLOSE,// 关闭
        MSGERROR,// 发送消息错误
    }

}
