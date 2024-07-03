package com.cyyaw.coco.dao;


import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.activity.MainActivity;
import com.cyyaw.coco.activity.home.adapter.ContentListAdapter;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.entity.ContentEntity;


/**
 * 内容
 */
public class ContentDao {


    private static String TAG = ContentDao.class.getName();

    /**
     * 用户登录
     */
    public static void loadContent(ContentListAdapter adapter, Integer page) {
        JSONObject json = new JSONObject();
        json.put("page", page);
        AppRequest.getRequest("http://192.168.0.103:8080/app/" + MyApplication.appId + "/content/findContent", json, (String body) -> {
            JSONObject js = JSONObject.parseObject(body);
            JSONArray dataList = js.getJSONArray("data");
            if (null != dataList) {
                for (int i = 0; i < dataList.size(); i++) {
                    JSONObject data = dataList.getJSONObject(i);
                    ContentEntity content = new ContentEntity();
                    content.setTid(data.getString("tid"));
                    content.setUserName(data.getString("userName"));
                    content.setUserId(data.getString("userId"));
                    content.setFace(data.getString("face"));
                    content.setContentText(data.getString("contentText"));
                    adapter.addData(content);
                }
            }
        });
    }


}
