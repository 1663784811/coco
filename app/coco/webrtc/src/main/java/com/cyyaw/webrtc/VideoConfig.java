package com.cyyaw.webrtc;

import android.content.Context;
import android.content.Intent;

public class VideoConfig {

    private static VideoConfig videoConfig;

    private VideoConfig() {
    }


    public static VideoConfig getInstance(Context context) {
        if (null == videoConfig) {
            new VideoConfig();
        }
        Intent intent = new Intent(context, VideoServer.class);
        context.startService(intent);
        return videoConfig;
    }
}
