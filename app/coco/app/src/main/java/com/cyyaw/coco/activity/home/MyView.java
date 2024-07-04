package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.SettingActivity;
import com.cyyaw.coco.activity.home.adapter.LinearLayoutManagerNonScrollable;
import com.cyyaw.coco.activity.home.adapter.MyContentListAdapter;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class MyView extends Fragment {


    private Context context;

    public MyView(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_my, container, false);
        View settingImgView = view.findViewById(R.id.settingImgView);
        settingImgView.setOnClickListener((View v) -> {
            SettingActivity.openActivity(context);
        });


        // 内容
        RecyclerView recyclerView = view.findViewById(R.id.contentList);
        LinearLayoutManager layoutManager = new LinearLayoutManagerNonScrollable(context);
        recyclerView.setLayoutManager(layoutManager);
        MyContentListAdapter contentListAdapter = new MyContentListAdapter(context);
        recyclerView.setAdapter(contentListAdapter);


        List<ContentEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setUserName("sss");
            dataList.add(contentEntity);
        }
        contentListAdapter.setDataList(dataList);
        return view;
    }

}
