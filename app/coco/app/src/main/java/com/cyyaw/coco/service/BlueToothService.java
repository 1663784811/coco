//package com.cyyaw.coco.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.NonNull;
//
//import com.cyyaw.coco.common.permission.PermissionsCode;
//
//public class BlueToothService extends Service {
//
//
//    /**
//     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
//     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
//     */
//    @Override
//    public void onCreate() {
//        System.out.println("onCreate invoke");
//        super.onCreate();
//    }
//
//
//
//    /**
//     * 绑定服务时才会调用
//     * 必须要实现的方法
//     * @param intent
//     * @return
//     */
//    @Override
//    public IBinder onBind(Intent intent) {
//
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//
//
//    /**
//     * 每次通过startService()方法启动Service时都会被回调。
//     */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        System.out.println("onStartCommand invoke");
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//
//
//    /**
//     * 服务销毁时的回调
//     */
//    @Override
//    public void onDestroy() {
//        System.out.println("onDestroy invoke");
//        super.onDestroy();
//    }
//
//
//
//
//
//}