package com.cyyaw.coco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.FriendsDao;


/**
 * 搜索页面
 */
public class SearchActivity extends AppCompatActivity implements FriendsDao.UpdateDataCallBack {


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        findViewById(R.id.searchBtn).setOnClickListener((View v) -> {

            FriendsDao.searchFriends(SearchActivity.this);

        });


    }



    @Override
    public void updateData() {




    }
}