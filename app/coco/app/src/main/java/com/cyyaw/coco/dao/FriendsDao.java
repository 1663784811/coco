package com.cyyaw.coco.dao;

import android.content.Context;
import android.util.Log;

import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.common.RunCallback;
import com.cyyaw.coco.common.network.AppRequest;
import com.cyyaw.coco.entity.FriendsEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 好友Dao
 */
public class FriendsDao {

    private static FriendsDao friendsDao;

    private static ChatInfoDatabaseHelper chatInfoDatabaseHelper;

    private static final String TAG = "FriendsDao";

    private FriendsDao() {
    }

    public static FriendsDao getInstance(Context context) {
        if (friendsDao == null) {
            synchronized (FriendsDao.class) {
                friendsDao = new FriendsDao();
                chatInfoDatabaseHelper = ChatInfoDatabaseHelper.getInstance(context);
            }
        }
        return friendsDao;
    }


    public List<FriendsEntity> getFriends(FriendsListAdapter adapter) {
        List<FriendsEntity> rest = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            FriendsEntity entity = new FriendsEntity();
            entity.setId(i);
            entity.setName("名称:" + i);
            rest.add(entity);
        }
        adapter.setDataList(rest);


        AppRequest.getRequest("http://192.168.0.103:8080/admin/aaa/common/query?code=select_ent_app_user&appId=sss&total=0&size=10&sort=&page=1", (String body) -> {
            //Log.d(TAG, "getFriends: " + body);

            try {
                JSONObject json = new JSONObject(body);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });


        return rest;
    }


}
