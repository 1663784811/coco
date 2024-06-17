package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.cyyaw.coco.R;

import java.util.ArrayList;
import java.util.List;

public class ChatListMsgView extends ConstraintLayout {
    public ChatListMsgView(Context context) {
        super(context);
        initView(context, null);
    }

    public ChatListMsgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public ChatListMsgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }



    private void initView(Context context, AttributeSet attrs) {
        //加载布局
        View chatView = LayoutInflater.from(context).inflate(R.layout.chat_list, this, true);




    }



}
