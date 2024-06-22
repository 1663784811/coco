package com.cyyaw.webrtc;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.cyyaw.webrtc.rtc.VoipEvent;
import com.cyyaw.webrtc.rtc.aaaa.webrtc.SkyEngineKit;
import com.cyyaw.webrtc.rtc.core.socket.SocketManager;

/**
 *
 */
public class WebRtcConfig {

    private static final String TAG = WebRtcConfig.class.getName();

    private static Context appContext;
    private static MediaPlayer mediaPlayer;

    public static void init(Context context) {
        appContext = context;
        SkyEngineKit.init(new VoipEvent());

        SocketManager.getInstance().connect("ws://192.168.0.103:3000/ws", "1111111", 0);

    }


    public static Context getContext() {
        return appContext;
    }


    public static void playAudioStart(Uri uri) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(appContext, uri);
        mediaPlayer.start();
    }

    public static void playAudioStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public static void logI(Object clazzObj, String msg) {
        if (null != clazzObj) {
            Log.i(TAG, "logE: " + clazzObj.getClass().getName() + "   " + msg);
        } else {
            Log.i(TAG, "logE: " + msg);
        }

    }

    public static void logE(Object clazzObj, String msg) {
        if (null != clazzObj) {
            Log.e(TAG, "logE: " + clazzObj.getClass().getName() + "   " + msg);
        } else {
            Log.e(TAG, "logE: " + msg);
        }

    }

}