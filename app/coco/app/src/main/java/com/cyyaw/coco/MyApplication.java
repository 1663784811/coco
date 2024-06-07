package com.cyyaw.coco;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private static final Handler sHandler = new Handler();
    // 单例Toast,避免重复创建，显示时间过长
    private static Toast sToast;

    // 线程池
    private static final Executor ThreadPool = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        // 注册Activity生命周期回调
        // registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }

    public static void toast(String txt) {
        toast(txt, Toast.LENGTH_SHORT);
    }

    public static void toast(String txt, int duration) {
        sToast.setText(txt);
        sToast.setDuration(duration);
        sToast.show();
    }

    public static void run(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void post(Runnable runnable) {
        ThreadPool.execute(runnable);
    }

}

