package com.cyyaw.coco.common;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class BroadcastData<T> implements Serializable {

    private String code;

    private T data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static BroadcastData getInstance(BroadcastEnum broadcastEnum, Object data) {
        BroadcastData broadcastData = new BroadcastData();
        broadcastData.setCode(broadcastEnum.getCode());
        broadcastData.setData(data);
        return broadcastData;
    }


}
