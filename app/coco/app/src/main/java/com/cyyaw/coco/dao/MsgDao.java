package com.cyyaw.coco.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.MsgEntity;

import java.util.ArrayList;
import java.util.List;

public class MsgDao {

    private static String TAG = MsgDao.class.getName();


    public static List<MsgEntity> getMsg() {


        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_info", new String[]{});





        return new ArrayList<>();
    }


}
