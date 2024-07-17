package com.cyyaw.coco.dao;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.dao.table.FriendsEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * 好友Dao
 */
public class FriendsDao {

    private static final String TAG = "FriendsDao";

    private static WeakReference<UpdateDataCallBack> updateDataCallBack;

    private FriendsDao() {
    }

    /**
     * 获取好友数据
     */
    public static List<FriendsEntity> getFriends() {
        List<FriendsEntity> rest = new ArrayList<>();
        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_info");
        if (null != arr && arr.size() > 0) {
            for (int i = 0; i < arr.size(); i++) {
                rest.add(arr.getObject(i, FriendsEntity.class));
            }
        }
        return rest;
    }

    public static void setUpdateDataCallBack(UpdateDataCallBack updateDataCallBack) {
        FriendsDao.updateDataCallBack = new WeakReference(updateDataCallBack);
        MyApplication.post(() -> {
            AppRequest.getRequest("http://192.168.0.103:8080/app/" + MyApplication.appId + "/friends/myFriends", (String body) -> {
                JSONObject json = JSONObject.parseObject(body);
                JSONArray data = json.getJSONArray("data");
                if (null != data) {
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject js = data.getJSONObject(i);
                        JSONObject user = js.getJSONObject("toUser");
                        FriendsEntity friends = new FriendsEntity();
                        // ===
                        friends.setTid(js.getString("toUserId"));
                        friends.setNickName(user.getString("nickName"));
                        friends.setNote(js.getString("note"));
                        friends.setAccount(user.getString("account"));
                        friends.setPhone(user.getString("phone"));
                        friends.setSex(user.getString("sex"));
                        String face = user.getString("face");
                        if (null != face && face.length() > 0) {
                            friends.setFace(face);
                        } else {
                            friends.setFace("https://m.360buyimg.com/babel/jfs/t1/60865/19/20309/4778/660157d4F65db1655/b869b66cd4a18079.png");
                        }
                        updateFriends(friends);
                    }
                    if (null != updateDataCallBack) {
                        updateDataCallBack.updateData();
                    }
                }
            });
        });
    }

    /**
     * 更新数据库数据
     */
    public static void updateFriends(FriendsEntity friends) {
        String tid = friends.getTid();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tid", tid);
        jsonObject.put("nickName", friends.getNickName());
        jsonObject.put("note", friends.getNote());
        jsonObject.put("account", friends.getAccount());
        jsonObject.put("phone", friends.getPhone());
        jsonObject.put("face", friends.getFace());
        jsonObject.put("sex", friends.getSex());
        JSONObject userJson = ChatInfoDatabaseHelper.queryDataOne("select * from user_info where tid = ? ", new String[]{tid});
        if (null != userJson) {
            jsonObject.put("id", userJson.get("id"));
            ChatInfoDatabaseHelper.updateByTid("user_info", jsonObject);
        } else {
            ChatInfoDatabaseHelper.insertData("user_info", jsonObject);
        }
    }


    /**
     * 更新数据回调
     */
    public interface UpdateDataCallBack {
        void updateData();
    }

}
