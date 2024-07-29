package com.cyyaw.coco.dao;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.UserMsgEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMsgDao {


    private UserMsgDao() {
    }


    public static List<UserMsgEntity> getList() {
        List<UserMsgEntity> rest = new ArrayList<>();
        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_msg_list order by updateTime");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = arr.getJSONObject(i);
            UserMsgEntity obj = json.toJavaObject(UserMsgEntity.class);
            rest.add(obj);
        }
        return rest;
    }


}
