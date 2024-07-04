package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.SettingActivity;

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
        return view;
    }

}
