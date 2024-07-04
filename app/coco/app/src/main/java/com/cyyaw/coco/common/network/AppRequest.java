package com.cyyaw.coco.common.network;


import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.common.RunCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * app 请求统一请求头处理
 */
public class AppRequest {

    public static String token = "ssss";

    // =================================================================================   GET
    public static void getRequest(String url, RunCallback<String> success) {
        getRequest(url, success, null);
    }

    public static void getRequest(String url, Map<String, Object> parameter, RunCallback<String> success) {
        getRequest(url, parameter, success, null);
    }

    public static void getRequest(String url, RunCallback<String> success, RunCallback<Exception> error) {
        getRequest(url, null, success, error);
    }

    public static void getRequest(String url, Map<String, Object> parameter, RunCallback<String> success, RunCallback<Exception> error) {
        Http.getRequest(url, parameter, success, error, getHeaders());
    }

    // =================================================================================   POST

    public static void postRequest(String url, JSONObject json, RunCallback<String> success) {
        postRequest(url, json, success, null);
    }

    public static void postRequest(String url, JSONObject json, RunCallback<String> success, RunCallback<Exception> error) {
        Http.postRequest(url, json, success, error, getHeaders());
    }

    private static Map<String, Object> getHeaders() {
        Map<String, Object> rest = new HashMap<>();
        rest.put("token", MyApplication.getToken());
        return rest;
    }


}
