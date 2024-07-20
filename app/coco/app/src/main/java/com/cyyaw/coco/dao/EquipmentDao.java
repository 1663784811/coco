package com.cyyaw.coco.dao;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.common.ChatInfoDatabaseHelper;
import com.cyyaw.coco.dao.table.EquipmentEntity;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDao {

    private static String TAG = EquipmentDao.class.getName();


    /**
     * 添加设备
     */
    public static void addEquipment(EquipmentEntity equipment) {
        String address = equipment.getAddress();
        JSONObject data = new JSONObject();
        data.put("type", equipment.getType());
        data.put("name", equipment.getName());
        data.put("address", equipment.getAddress());
        data.put("imgUrl", equipment.getImgUrl());
        JSONObject json = ChatInfoDatabaseHelper.queryDataOne("select * from equipment where address = ?", new String[]{address});
        if (json != null) {
            data.put("id", json.get("id"));
            ChatInfoDatabaseHelper.updateById("equipment", data);
        } else {
            ChatInfoDatabaseHelper.insertData("equipment", data);
        }
    }


    public static List<EquipmentEntity> equipmentList() {
        List<EquipmentEntity> rest = new ArrayList<>();
        JSONArray objects = ChatInfoDatabaseHelper.queryData("select * from equipment");
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            rest.add(jsonObject.toJavaObject(EquipmentEntity.class));
        }
        return rest;
    }


    /**
     * 删除
     */
    public static void deleteEquipment(String address) {
        JSONObject json = ChatInfoDatabaseHelper.queryDataOne("select * from equipment where address = ?", new String[]{address});
        if (null != json) {
            String id = json.getString("id");
            ChatInfoDatabaseHelper.deleteById("equipment", id);
        }
    }
}
