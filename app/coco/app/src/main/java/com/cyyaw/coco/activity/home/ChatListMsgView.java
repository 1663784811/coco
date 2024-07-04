package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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

public class ChatListMsgView extends Fragment implements FriendsListAdapter.ListenerFriends {

    private Context context;

    public ChatListMsgView(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list, container, false);
        //加载布局
        RecyclerView recyclerView = view.findViewById(R.id.chatList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(context, this);
        recyclerView.setAdapter(friendsListAdapter);
        // 请求网络

        // 同步好友
        FriendsDao instance = FriendsDao.getInstance(context);

        instance.getFriends(friendsListAdapter);

        return view;
    }


    @Override
    public void click(View v, FriendsEntity friendsEntity) {
        MessageActivity.openActivity(context, friendsEntity.getTid(), friendsEntity.getNickName(), friendsEntity.getFace());
    }
}
