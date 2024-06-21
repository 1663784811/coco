package com.cyyaw.webrtc;

import android.content.Context;
import android.content.Intent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoConfig {

    public final static String IP = "192.168.0.103:3000";

    private final static String HOST = "http://" + IP + "/";

    // 信令地址
    public final static String WS = "ws://" + IP + "/ws";

    private static VideoConfig videoConfig;


    public static  ExecutorService executor = Executors.newCachedThreadPool();


    private VideoConfig() {
    }


    public static VideoConfig getInstance(Context context) {
        if (null == videoConfig) {
            videoConfig = new VideoConfig();
        }
        Intent intent = new Intent(context, VideoServer.class);
        context.startService(intent);
        return videoConfig;
    }
}
