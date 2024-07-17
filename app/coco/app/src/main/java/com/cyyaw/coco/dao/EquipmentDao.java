package com.cyyaw.coco.dao;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.EquipmentEntity;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDao {

    private static String TAG = EquipmentDao.class.getName();


    public static List<EquipmentEntity> equipmentList() {
        List<EquipmentEntity> rest = new ArrayList<>();
        JSONArray objects = ChatInfoDatabaseHelper.queryData("select * from equipment");
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            rest.add(jsonObject.toJavaObject(EquipmentEntity.class));
        }
        return rest;
    }


}
