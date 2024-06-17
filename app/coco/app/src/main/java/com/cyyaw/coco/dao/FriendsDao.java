package com.cyyaw.coco.dao;

import android.content.Context;

import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.entity.FriendsEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendsDao {

    private static FriendsDao friendsDao;

    private static ChatInfoDatabaseHelper chatInfoDatabaseHelper;

    private FriendsDao() {


    }

    public static FriendsDao getInstance(Context context) {
        if (friendsDao == null) {
            friendsDao = new FriendsDao();
            chatInfoDatabaseHelper = ChatInfoDatabaseHelper.getInstance(context);
        }
        return friendsDao;
    }


    public List<FriendsEntity> getFriends() {
        List<FriendsEntity> rest = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            FriendsEntity entity = new FriendsEntity();
            entity.setId(i);
            entity.setName("名称:" + i);
            rest.add(entity);
        }
        return rest;
    }


}
