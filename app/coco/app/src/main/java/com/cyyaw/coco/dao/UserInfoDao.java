package com.cyyaw.coco.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.dao.table.UserInfoEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * 好友Dao
 */
public class UserInfoDao {

    private static final String TAG = "FriendsDao";

    private static WeakReference<UpdateDataCallBack> updateDataCallBack;

    private UserInfoDao() {
    }


    public static void delFriendsBtn(String tid, UpdateDataCallBack updateDataCallBack) {
        // 删除远程
        JSONObject json = new JSONObject();
        json.put("tid", tid);
        AppRequest.postRequest(MyApplication.baseUrl + "/app/" + MyApplication.appId + "/friends/delFriends", json, (String body) -> {
            // 删除本地
            JSONObject userJson = ChatInfoDatabaseHelper.queryDataOne("select * from user_info where tid = ? ", new String[]{tid});
            if (null != userJson) {
                String id = userJson.getString("id");
                ChatInfoDatabaseHelper.deleteById("user_info", id);
            }
            updateDataCallBack.updateData();
        });
    }

    /**
     * 获取好友数据
     */
    public static List<UserInfoEntity> getFriends() {
        List<UserInfoEntity> rest = new ArrayList<>();
        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_info");
        if (null != arr && arr.size() > 0) {
            for (int i = 0; i < arr.size(); i++) {
                rest.add(arr.getObject(i, UserInfoEntity.class));
            }
        }
        return rest;
    }

    public static void setUpdateDataCallBack(UpdateDataCallBack updateDataCallBack) {
        UserInfoDao.updateDataCallBack = new WeakReference(updateDataCallBack);
        MyApplication.post(() -> {
            AppRequest.getRequest(MyApplication.baseUrl + "/app/" + MyApplication.appId + "/friends/myFriends", (String body) -> {
                JSONObject json = JSONObject.parseObject(body);
                JSONArray data = json.getJSONArray("data");
                if (null != data) {
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject js = data.getJSONObject(i);
                        JSONObject user = js.getJSONObject("toUser");
                        UserInfoEntity friends = new UserInfoEntity();
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
    public static void updateFriends(UserInfoEntity friends) {
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
     * 查询好友
     */
    public static void searchFriends(UpdateDataCallBack updateDataCallBack) {
        MyApplication.post(() -> {
            AppRequest.getRequest(MyApplication.baseUrl + "/app/" + MyApplication.appId + "/friends/searchFriends", (String body) -> {
                JSONObject json = JSONObject.parseObject(body);
                JSONArray data = json.getJSONArray("data");
                if (null != data) {
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject js = data.getJSONObject(i);
                        JSONObject user = js.getJSONObject("toUser");
                        UserInfoEntity friends = new UserInfoEntity();
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
     * 更新数据回调
     */
    public interface UpdateDataCallBack {
        void updateData();
    }

}
