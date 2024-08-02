package com.cyyaw.coco.common;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyyaw.coco.MyApplication;

import java.util.Map;

public class ChatInfoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = ChatInfoDatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "chat_info.db";
    private static final int DATABASE_VERSION = 8;
    private static ChatInfoDatabaseHelper chatInfoDatabaseHelper = null;

    private SQLiteDatabase mRead;
    private SQLiteDatabase mWrite;


    private ChatInfoDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        if (chatInfoDatabaseHelper == null) {
            synchronized (ChatInfoDatabaseHelper.class) {
                if (null == chatInfoDatabaseHelper) {
                    chatInfoDatabaseHelper = new ChatInfoDatabaseHelper(context);
                }
            }
        }
    }

    /**
     * 查询一条数据
     */
    public static JSONObject queryDataOne(String sql, String[] strings) {
        JSONArray arr = queryData(sql, strings);
        if (null != arr && arr.size() > 0) {
            return arr.getJSONObject(0);
        } else {
            return null;
        }
    }


    /**
     * 查询数据
     */
    public static JSONArray queryData(String sql) {
        return queryData(sql, new String[]{});
    }

    public static JSONArray queryData(String sql, String[] selectionArgs) {
        JSONArray rest = new JSONArray();
        try {
            if (null != chatInfoDatabaseHelper) {
                Cursor cursor = chatInfoDatabaseHelper.openReadConnect().rawQuery(sql, selectionArgs);
                while (cursor.moveToNext()) {
                    String[] columnNames = cursor.getColumnNames();
                    JSONObject json = new JSONObject();
                    for (int i = 0; i < columnNames.length; i++) {
                        String key = columnNames[i];
                        @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex(key));
                        json.put(key, value);
                    }
                    rest.add(json);
                }
            } else {
                MyApplication.toast("数据库异常, 没被初始化...");
            }
        } catch (Exception e) {
            String message = e.getMessage();
            e.printStackTrace();
            Log.e(TAG, "queryData  错误 : " + message);
        }
        return rest;
    }

    /**
     * 插入数据
     */
    public static void insertData(String table, Map<String, Object> data) {
        if (null != chatInfoDatabaseHelper) {
            ContentValues values = new ContentValues();
            for (String key : data.keySet()) {
                values.put(key, data.get(key) + "");
            }
            chatInfoDatabaseHelper.openWriteConnect().insert(table, null, values);
        } else {
            MyApplication.toast("数据库异常, 没被初始化...");
        }
    }

    public static void updateById(String table, Map<String, Object> data) {
        if (null != chatInfoDatabaseHelper) {
            ContentValues values = new ContentValues();
            for (String key : data.keySet()) {
                values.put(key, data.get(key) + "");
            }
            String id = data.get("id") + "";
            chatInfoDatabaseHelper.openWriteConnect().update(table, values, "id = ?", new String[]{id});
        } else {
            MyApplication.toast("数据库异常, 没被初始化...");
        }
    }

    public static void updateByTid(String table, Map<String, Object> data) {
        if (null != chatInfoDatabaseHelper) {
            ContentValues values = new ContentValues();
            for (String key : data.keySet()) {
                values.put(key, data.get(key) + "");
            }
            String tid = data.get("tid") + "";
            String id = data.get("id") + "";
            chatInfoDatabaseHelper.openWriteConnect().update(table, values, "id = ? and tid = ?", new String[]{id, tid});
        } else {
            MyApplication.toast("数据库异常, 没被初始化...");
        }
    }

    /**
     * 删除
     */
    public static void deleteById(String table, String id) {
        if (null != chatInfoDatabaseHelper) {
            chatInfoDatabaseHelper.openWriteConnect().delete(table, "id = ?", new String[]{id});
        } else {
            MyApplication.toast("数据库异常, 没被初始化...");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表的 SQL 语句
        Log.e(TAG, "onCreate: 创建表的 SQL 语句");
        // 用户信息
        db.execSQL("create table user_info( id INTEGER PRIMARY KEY AUTOINCREMENT, tid varch(32), nickName varchar(255), note varchar(255), account varchar(255), phone varchar(20), face text,  sex varchar(4))");
        // 消息列表
        db.execSQL("create table user_msg_list( id INTEGER PRIMARY KEY AUTOINCREMENT, tid varch(32), name varchar(255), face text,updateTime datetime)");
        // 消息记录
        db.execSQL("create table user_msg_log( id INTEGER PRIMARY KEY AUTOINCREMENT, tid varch(32), sendUserId varchar(32), receiveUserId varchar(32), msg text, sendTime datetime,status int )");
        // 设备数据
        db.execSQL("create table equipment(id INTEGER PRIMARY KEY AUTOINCREMENT, type int, name varchar(255),address varchar(255),imgUrl varchar(255) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新表结构的 SQL 语句
        Log.e(TAG, "onUpgrade: 更新表结构的 SQL 语句");
        db.execSQL("DROP TABLE IF EXISTS user_info");
        db.execSQL("DROP TABLE IF EXISTS user_msg_list");
        db.execSQL("DROP TABLE IF EXISTS user_msg_log");
        db.execSQL("DROP TABLE IF EXISTS equipment");
        onCreate(db);
    }


    public SQLiteDatabase openReadConnect() {
        if (null == mRead || !mRead.isOpen()) {
            mRead = getReadableDatabase();
        }
        return mRead;
    }

    public SQLiteDatabase openWriteConnect() {
        if (null == mWrite || !mWrite.isOpen()) {
            mWrite = getWritableDatabase();
        }
        return mWrite;
    }


}
