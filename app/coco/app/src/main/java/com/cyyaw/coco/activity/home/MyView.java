package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.SettingActivity;
import com.cyyaw.coco.activity.home.adapter.ContentEntityArrayAdapter;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class MyView extends Fragment {

    private static final String TAG = MyView.class.getName();


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
//        RecyclerView recyclerView = view.findViewById(R.id.contentList);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);
//        MyContentListAdapter contentListAdapter = new MyContentListAdapter(context);
//        recyclerView.setAdapter(contentListAdapter);


        List<ContentEntity> dataList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            ContentEntity contentEntity = new ContentEntity();
//            contentEntity.setUserName("sss");
//            dataList.add(contentEntity);
//        }
        //  contentListAdapter.setDataList(dataList);


        ListView listView = view.findViewById(R.id.contentListView);
        ListAdapter listAdapter = new ContentEntityArrayAdapter(context, dataList);
        listView.setAdapter(listAdapter);


        return view;
    }

}
