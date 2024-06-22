package com.cyyaw.webrtc;


import com.cyyaw.webrtc.rtc.core.socket.SocketManager;

/**
 *
 */
public class WebRtcConfig {


    public static void init() {

//        SkyEngineKit.init(new VoipEvent());

        SocketManager.getInstance().connect( "ws://192.168.0.103:3000/ws", "1111111", 0);

    }


}