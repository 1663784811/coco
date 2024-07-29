package com.cyyaw.coco.dao;


import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.activity.MainActivity;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.entity.UserInfo;

public class LoginDao {


    private static String TAG = LoginDao.class.getName();

    /**
     * 用户登录
     */
    public static void userLogin(Activity context, String userName, String password) {
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("password", password);
        AppRequest.postRequest("http://192.168.0.103:8080/app/" + MyApplication.appId + "/login/login", json, (String body) -> {
            // 登录成功, 保存token
            JSONObject js = JSONObject.parseObject(body);
            JSONObject data = js.getJSONObject("data");
            MyApplication.saveToken(data.getString("jwtToken"));
            UserInfo userInfo = data.getObject("uuser", UserInfo.class);
            MyApplication.saveLoginInfo(userInfo);
            MainActivity.openActivity(context);
            context.finish();
        });

    }


}
