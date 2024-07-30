package com.cyyaw.coco.dao;

import com.alibaba.fastjson.JSONArray;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.UserMsgLogEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class UserMsgLogDao {

    private static String TAG = UserMsgLogDao.class.getName();


    /**
     * 查询某个时间节点之前的
     */
    public static List<UserMsgLogEntity> getMsgLog(String sendId, String receiveId, Date time) {
        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        JSONArray arr = ChatInfoDatabaseHelper.queryData("select * from user_msg where sendUserId in (?,?) and receiveUserId in (?,?) and sendTime < ? limit 10", new String[]{sendId, receiveId, sendId, receiveId, timeStr});
        List<UserMsgLogEntity> rest = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            rest.add(arr.getObject(i, UserMsgLogEntity.class));
        }
        return rest;
    }


}
