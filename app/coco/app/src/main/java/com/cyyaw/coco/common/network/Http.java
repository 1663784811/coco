package com.cyyaw.coco.common.network;

import android.util.Log;

import com.cyyaw.coco.common.RunCallback;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http {

    private static final String TAG = "Http";

    private static OkHttpClient client = new OkHttpClient();


    public static void getRequest(String url, Map<String, Objects> parameter, RunCallback<String> success, RunCallback<IOException> error, Map<String, Object> headers) {


        Request request = getRB(headers).url(url).get().build();
        Call call = client.newBuilder().build().newCall(request);
        runCall(call, success, error);
    }

    public static void postRequest(String url, Map<String, Objects> parameter, RunCallback<String> success, RunCallback<IOException> error, Map<String, Object> headers) {
        RequestBody requestBody = null;

        Request request = getRB(headers).url(url).post(requestBody).build();
        Call call = client.newBuilder().build().newCall(request);
        runCall(call, success, error);
    }

    private static void runCall(Call call, RunCallback<String> success, RunCallback<IOException> error) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure;" + e.getLocalizedMessage());
                error.run(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                success.run(response.body().string());
            }
        });
    }

    private static Request.Builder getRB(Map<String, Object> headers) {
        Request.Builder builder = new Request.Builder();
        if (null != headers) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key).toString());
            }
        }
        return builder;
    }


}
