package com.cyyaw.coco.dao;

import com.alibaba.fastjson.JSONArray;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.UserMsgLogEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMsgLogDao {

    private static String TAG = UserMsgLogDao.class.getName();


    public static List<UserMsgLogEntity> getMsgLog() {


        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_msg", new String[]{});





        return new ArrayList<>();
    }


}
