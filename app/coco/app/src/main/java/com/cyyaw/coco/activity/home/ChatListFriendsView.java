package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.PersonCenterActivity;
import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.entity.FriendsEntity;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.List;

public class ChatListFriendsView extends ConstraintLayout implements FriendsListAdapter.ListenerFriends {
    private static final String TAG = "ChatListFriendsView";

    private Context context;

    public ChatListFriendsView(Context context) {
        super(context);
        initView(context, null);
    }

    public ChatListFriendsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public ChatListFriendsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }


    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        //加载布局
        View chatView = LayoutInflater.from(context).inflate(R.layout.chat_friends, this, true);
        RecyclerView recyclerView = chatView.findViewById(R.id.friendsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(context, this);
        recyclerView.setAdapter(friendsListAdapter);
        // 同步好友
        FriendsDao instance = FriendsDao.getInstance(context);
        List<FriendsEntity> friends = instance.getFriends(friendsListAdapter);
    }


    @Override
    public void click(View v, FriendsEntity friendsEntity) {
        // 跳转个人中心页面
        ActivityUtils.startActivity(context, PersonCenterActivity.class, friendsEntity);
    }
}
