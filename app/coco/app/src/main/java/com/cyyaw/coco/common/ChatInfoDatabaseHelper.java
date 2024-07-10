package com.cyyaw.coco.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatInfoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = ChatInfoDatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "chat_info.db";
    private static final int DATABASE_VERSION = 1;
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
        String CREATE_TABLE = "CREATE TABLE mytable ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新表结构的 SQL 语句
        Log.e(TAG, "onUpgrade: 更新表结构的 SQL 语句");
        db.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(db);
    }


    public void openReadConnect() {
        if (null == mRead || !mRead.isOpen()) {
            mRead = chatInfoDatabaseHelper.getReadableDatabase();
        }
    }

    public void openWriteConnect() {
        if (null == mWrite || !mWrite.isOpen()) {
            mWrite = chatInfoDatabaseHelper.getWritableDatabase();
        }
    }


}
