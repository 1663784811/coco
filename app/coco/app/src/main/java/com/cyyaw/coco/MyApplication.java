package com.cyyaw.coco;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.widget.Toast;

//import com.cyyaw.webrtc.VideoConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private static final Handler sHandler = new Handler();
    // 单例Toast,避免重复创建，显示时间过长
    private static Toast sToast;

    public static final Map<String, BluetoothDevice> blueTooth = new ConcurrentHashMap<>();

    // 线程池
    private static final Executor ThreadPool = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        // 注册Activity生命周期回调
        // registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());


        // 语音通话配置
       //  VideoConfig.getInstance(this);





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

    public static void run(Runnable runnable) {
        ThreadPool.execute(runnable);
    }

}


