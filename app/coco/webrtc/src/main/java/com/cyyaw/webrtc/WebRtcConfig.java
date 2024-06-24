package com.cyyaw.webrtc;


import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cyyaw.webrtc.page.VoipEvent;
import com.cyyaw.webrtc.rtc.SkyEngineKit;
import com.cyyaw.webrtc.socket.SocketManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 *
 */
public class WebRtcConfig {
    private static final String TAG = WebRtcConfig.class.getName();
    public static final Handler handler = new Handler(Looper.getMainLooper());
    public static String socketUrl = "ws://192.168.0.103:3000/ws";
    private static Context appContext;
    private static MediaPlayer mediaPlayer;
    private static List<Activity> activityList = new CopyOnWriteArrayList<>();


    /**
     * 初始化
     */
    public static void init(Application context) {
        appContext = context;
        SocketManager.getInstance().connect(socketUrl, "111", 0);
        SkyEngineKit.init(new VoipEvent());


        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                // 当 Activity 被创建时调用此方法
                // 例如：Activity 刚刚调用了 onCreate() 方法
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                // 当 Activity 进入前台并开始与用户交互时调用此方法
                // 例如：Activity 刚刚调用了 onStart() 方法
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                // 当 Activity 被恢复到前台并开始与用户交互时调用此方法
                // 例如：Activity 刚刚调用了 onResume() 方法
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                // 当 Activity 被暂停时调用此方法
                // 例如：Activity 即将调用 onPause() 方法
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                // 当 Activity 不再处于前台，不再与用户交互时调用此方法
                // 例如：Activity 即将调用 onStop() 方法
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                // 当 Activity 需要保存其状态时调用此方法
                // 例如：系统即将销毁 Activity，但希望在将来恢复时保存当前状态
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                // 当 Activity 被销毁时调用此方法
                // 例如：Activity 刚刚调用了 onDestroy() 方法
                activityList.remove(activity); // 从 activityList 中移除被销毁的 Activity
            }
        });

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

    /**
     * 启动新的active
     */
    public static void startActivity(Intent intent, Class clazz) {
        if (activityList.size() > 0) {
            Activity activity = activityList.get(0);
            intent.setComponent(new ComponentName(activity.getPackageName(), clazz.getName()));
            activity.startActivity(intent);
        }
    }
}