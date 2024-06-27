package com.cyyaw.coco.common.network;


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

    public static void getRequest(String url, Map<String, Objects> parameter, RunCallback<String> success) {
        getRequest(url, parameter, success, null);
    }

    public static void getRequest(String url, RunCallback<String> success, RunCallback<IOException> error) {
        getRequest(url, null, success, error);
    }

    public static void getRequest(String url, Map<String, Objects> parameter, RunCallback<String> success, RunCallback<IOException> error) {
        Http.getRequest(url, parameter, success, error, getHeaders());
    }

    // =================================================================================   POST

    public static void postRequest(String url, Map<String, Objects> parameter, RunCallback<String> success) {
        postRequest(url, parameter, success, null);
    }

    public static void postRequest(String url, Map<String, Objects> parameter, RunCallback<String> success, RunCallback<IOException> error) {
        Http.postRequest(url, parameter, success, error, getHeaders());
    }

    private static Map<String, Object> getHeaders() {
        Map<String, Object> rest = new HashMap<>();
        rest.put("token", "");
        return rest;
    }


}
