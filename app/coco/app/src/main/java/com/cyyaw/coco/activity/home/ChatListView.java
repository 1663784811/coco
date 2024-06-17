package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatListView extends ConstraintLayout {

    private Context context;

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
        this.context = context;
        //加载布局
        View chatView = LayoutInflater.from(context).inflate(R.layout.activity_main_chat, this, true);
        ViewPager vp = chatView.findViewById(R.id.char_ViewPager);
        List<View> pageData = new ArrayList<>();
        pageData.add(new ChatListMsgView(context));
        pageData.add(new ChatListFriendsView(context));
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(context, pageData);
        vp.setAdapter(myPagerAdapter);
        vp.setCurrentItem(0);
    }


}
