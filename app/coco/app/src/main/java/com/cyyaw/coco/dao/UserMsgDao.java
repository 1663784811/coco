package com.cyyaw.coco.dao;

import com.alibaba.fastjson.JSONArray;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;

public class UserMsgDao {



    private UserMsgDao() {
    }





    public static void getList() {
        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_msg_list");





    }





}
