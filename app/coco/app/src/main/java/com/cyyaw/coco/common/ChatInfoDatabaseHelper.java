package com.cyyaw.coco.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatInfoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = ChatInfoDatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "chat_info.db";
    private static final int DATABASE_VERSION = 2;
    private static ChatInfoDatabaseHelper chatInfoDatabaseHelper = null;

    private SQLiteDatabase mRead;
    private SQLiteDatabase mWrite;


    private ChatInfoDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ChatInfoDatabaseHelper getInstance() {
        return chatInfoDatabaseHelper;
    }

    public static void init(Context context) {
        if (chatInfoDatabaseHelper == null) {
            synchronized (ChatInfoDatabaseHelper.class) {
                if (null != chatInfoDatabaseHelper) {
                    chatInfoDatabaseHelper = new ChatInfoDatabaseHelper(context);
                }
            }
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表的 SQL 语句
        Log.e(TAG, "onCreate: 创建表的 SQL 语句");
        // 用户信息
        db.execSQL("create table user_info( " +
                "id INTEGER PRIMARY KEY, tid varch(32), nickName varchar(255), note varchar(255)," +
                " account varchar(255), phone varchar(20), face text,  sex varchar(4))  ");

//        db.execSQL(" ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新表结构的 SQL 语句
        Log.e(TAG, "onUpgrade: 更新表结构的 SQL 语句");
        db.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(db);
    }


    public SQLiteDatabase openReadConnect() {
        if (null == mRead || !mRead.isOpen()) {
            mRead = getReadableDatabase();
        }
        return mRead;
    }

    public void openWriteConnect() {
        if (null == mWrite || !mWrite.isOpen()) {
            mWrite = chatInfoDatabaseHelper.getWritableDatabase();
        }
    }


}
