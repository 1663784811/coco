package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.ScanQrCodeActivity;
import com.cyyaw.coco.activity.SettingActivity;
import com.cyyaw.coco.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {

    private static final String TAG = MyFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_my, container, false);
        View settingImgView = view.findViewById(R.id.settingImgView);
        settingImgView.setOnClickListener((View v) -> {
            SettingActivity.openActivity(getContext());
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


//        ListView listView = view.findViewById(R.id.contentListView);
//        ListAdapter listAdapter = new ContentEntityArrayAdapter(context, dataList);
//        listView.setAdapter(listAdapter);


        View userBackImage = view.findViewById(R.id.userBackImage);
        userBackImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyApplication.toast("长按修改背景");
                return true;
            }
        });


        view.findViewById(R.id.scanBtn).setOnClickListener((View v) -> {
            ScanQrCodeActivity.openActivity(getContext());
        });


        return view;
    }

}
