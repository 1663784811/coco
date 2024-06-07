package com.cyyaw.coco.utils.print;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是打印队列。
 * 你可以简单地添加打印字节来排队。 并且这个类将这些字节发送到蓝牙设备
 */
public class PrintQueue {

    /**
     * instance
     */
    private static PrintQueue mInstance;
    /**
     * print queue  打印机队列
     */
    private List<byte[]> mQueue;

    private PrintQueue() {
    }

    public static PrintQueue getQueue() {
        if (null == mInstance) {
            mInstance = new PrintQueue();
        }
        return mInstance;
    }

    /**
     * add print bytes to queue. and call print
     */
    public synchronized void add(List<byte[]> data) {
        if (null == mQueue) {
            mQueue = new ArrayList<byte[]>();
        }
        if (null != data) {
            mQueue.addAll(data);
        }
        print();
    }

    /**
     * print queue
     */
    public synchronized void print() {
        while (mQueue.size() > 0) {
            byte[] bytes = mQueue.get(0);
//            for (int i = 0; i < bytes.length; i += 48) {
//                for (int j = 0; j < 48; j++) {
//                    System.out.print(bytes[i + j]);
//                }
//                System.out.println("");
//            }
            System.out.println("换行:" + bytes.length);
            mQueue.remove(0);
        }
        System.out.println("===");
    }
}
