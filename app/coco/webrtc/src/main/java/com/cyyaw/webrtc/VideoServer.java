package com.cyyaw.webrtc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cyyaw.webrtc.fragment.VoipEvent;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.socket.SocketManager;


/**
 * 视频服务
 */
public class VideoServer extends Service {

    private static final String TAG = VideoServer.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ssssssssssssssssssssssss");
        // 设置接收广播
//        new VideoBroadcastReceiver(this, this);
        SkyEngineKit.init(new VoipEvent());
        // 连接服务器
        SocketManager.getInstance().connect(VideoConfig.WS, "1111", 0);




    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
