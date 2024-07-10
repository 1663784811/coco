package com.cyyaw.coco.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.PersonCenterActivity;
import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.entity.FriendsEntity;
import com.cyyaw.coco.utils.ActivityUtils;

import java.util.List;


/**
 * 好友列表
 */
public class ChatListFriendsView extends Fragment implements FriendsListAdapter.ListenerFriends {

    private static final String TAG = "ChatListFriendsView";

    private Context context;

    public ChatListFriendsView(Context context) {
        this.context = context;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_friends, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.friendsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(context, this);
        recyclerView.setAdapter(friendsListAdapter);
        // 同步好友
        FriendsDao instance = FriendsDao.getInstance();
        instance.getFriends(friendsListAdapter);

        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        return view;
    }


    @Override
    public void click(View v, FriendsEntity friendsEntity) {
        // 跳转个人中心页面
        PersonCenterActivity.openActivity(context, friendsEntity.getTid(), friendsEntity.getNickName(), friendsEntity.getFace());
    }
}
