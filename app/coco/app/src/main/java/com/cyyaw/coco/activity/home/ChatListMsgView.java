package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.MessageActivity;
import com.cyyaw.coco.activity.PersonCenterActivity;
import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.entity.FriendsEntity;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatListMsgView extends ConstraintLayout implements FriendsListAdapter.ListenerFriends {

    private Context context;

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
        this.context = context;
        //加载布局
        View chatView = LayoutInflater.from(context).inflate(R.layout.chat_list, this, true);
        //加载布局
        RecyclerView recyclerView = chatView.findViewById(R.id.chatList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(context, this);
        recyclerView.setAdapter(friendsListAdapter);
        // 请求网络

        // 同步好友
        FriendsDao instance = FriendsDao.getInstance(context);

        instance.getFriends(friendsListAdapter);

    }


    @Override
    public void click(View v, FriendsEntity friendsEntity) {
        ActivityUtils.startActivity(context, MessageActivity.class, friendsEntity);
    }
}
