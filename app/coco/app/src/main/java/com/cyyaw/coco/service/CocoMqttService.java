package com.cyyaw.coco.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cyyaw.coco.common.MqttHelper;


/**
 * mqtt
 */
public class CocoMqttService extends Service {
    private static final String TAG = "MqttService";
    private MqttHelper mqttHelper;





    @Override
    public void onCreate() {
        super.onCreate();
        mqttHelper = new MqttHelper(this, "", "");
        Log.d(TAG, "MqttService created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MqttService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mqttHelper.disconnect();
        Log.d(TAG, "MqttService destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // 不需要绑定服务，返回null
    }


}