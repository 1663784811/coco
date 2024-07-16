package com.cyyaw.coco.dao;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.common.network.BaseResult;
import com.cyyaw.coco.entity.FriendsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 好友Dao
 */
public class FriendsDao {

    private static FriendsDao friendsDao;


    private static final String TAG = "FriendsDao";

    private FriendsDao() {
    }

    public static FriendsDao getInstance() {
        if (friendsDao == null) {
            synchronized (FriendsDao.class) {
                friendsDao = new FriendsDao();
            }
        }
        return friendsDao;
    }


    public List<FriendsEntity> getFriends(FriendsListAdapter adapter) {
        List<FriendsEntity> rest = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            FriendsEntity entity = new FriendsEntity();
            entity.setId(i);
            entity.setNickName("名称:" + i);
            rest.add(entity);
        }
        adapter.setDataList(rest);

        MyApplication.run(()->{
            //        ChatInfoDatabaseHelper.getInstance().openReadConnect().query();
            AppRequest.getRequest("http://192.168.0.103:8080/app/" + MyApplication.appId + "/friends/myFriends", (String body) -> {
                JSONObject json = JSONObject.parseObject(body);
                JSONArray data = json.getJSONArray("data");
                if (null != data) {
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject js = data.getJSONObject(i);
                        FriendsEntity friends = new FriendsEntity();
                        JSONObject user = js.getJSONObject("toUser");
                        friends.setId(user.getInteger("id"));
                        String face = user.getString("face");
                        if (null != face && face.length() > 0) {
                            friends.setFace(face);
                        } else {
                            friends.setFace("https://m.360buyimg.com/babel/jfs/t1/60865/19/20309/4778/660157d4F65db1655/b869b66cd4a18079.png");
                        }
                        friends.setNickName(user.getString("trueName"));
                        if(null != adapter){
                            adapter.updateData(friends);
                        }
                    }
                }
                Log.i(TAG, "getFriends: " + body);
            });
        });
        return rest;
    }


}
