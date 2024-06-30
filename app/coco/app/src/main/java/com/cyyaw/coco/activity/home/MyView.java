package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.SettingActivity;

public class MyView extends ConstraintLayout {
    public MyView(Context context) {
        super(context);
        initView(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        //加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_my, this, true);
        View settingImgView = view.findViewById(R.id.settingImgView);
        settingImgView.setOnClickListener((View v) -> {
            SettingActivity.openActivity(context);
        });

    }
}
