package com.cyyaw.coco.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.FriendsListAdapter;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.dao.table.FriendsEntity;

import java.util.List;


/**
 * 好友列表
 */
public class ChatListFriendsView extends Fragment implements FriendsDao.UpdateDataCallBack {

    private static final String TAG = "ChatListFriendsView";

    private Context context;

    private FriendsListAdapter friendsListAdapter;


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
        friendsListAdapter = new FriendsListAdapter(context);
        recyclerView.setAdapter(friendsListAdapter);
        // 同步好友
        updateData();
        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        FriendsDao.setUpdateDataCallBack(this);
        return view;
    }

    @Override
    public void updateData() {
        List<FriendsEntity> friendsList = FriendsDao.getFriends();
        friendsListAdapter.setDataList(friendsList);
    }
}
