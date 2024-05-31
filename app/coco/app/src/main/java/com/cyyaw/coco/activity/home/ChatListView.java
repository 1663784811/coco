package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cyyaw.coco.R;

public class ChatListView  extends ConstraintLayout {
    public ChatListView(Context context) {
        super(context);
        initView(context, null);
    }

    public ChatListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public ChatListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }



    private void initView(Context context, AttributeSet attrs) {
        //加载布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main_chatlist, this, true);
    }



}
