package com.cyyaw.coco;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.webrtc.net.StatusCallBack;
import com.cyyaw.webrtc.WebRtcConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {


    public static final String appId = "sss";
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    // 单例Toast,避免重复创建，显示时间过长
    private static Toast sToast;
    // 蓝牙列表
    public static final Map<String, BluetoothDevice> blueTooth = new ConcurrentHashMap<>();
    // 线程池
    private static final Executor ThreadPool = Executors.newCachedThreadPool();

    private static Application appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        ChatInfoDatabaseHelper.init(this);
        WebRtcConfig.init(appContext, "111111", "sssss", (StatusCallBack.NetStatus netStatus, String msg) -> {
            // 回调
        });
        // 初始化蓝牙
        BlueToothManager.init(appContext);

    }

    public static void toast(String txt) {
        toast(txt, Toast.LENGTH_SHORT);
    }

    public static void toast(String txt, int duration) {
        sToast.setText(txt);
        sToast.setDuration(duration);
        sToast.show();
    }

    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void post(Runnable runnable, long uptimeMillis) {
        sHandler.postDelayed(runnable, uptimeMillis);
    }


    public static void run(Runnable runnable) {
        ThreadPool.execute(runnable);
    }


    // 保存Token的方法
    public static void saveToken(String token) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }

    public static String getToken() {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);
        return authToken;
    }

    public static boolean checkToken() {
        String authToken = getToken();
        if (null != authToken && authToken.length() > 0) {
            return true;
        }
        return false;
    }


}


