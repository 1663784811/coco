package com.cyyaw.webrtc;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.widget.Toast;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 语音通话配置
        WebRtcConfig.init(this);
    }

}


