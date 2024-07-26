package com.cyyaw.coco.activity.home;

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
import com.cyyaw.coco.activity.home.adapter.MsgListAdapter;
import com.cyyaw.coco.dao.MsgDao;
import com.cyyaw.coco.dao.table.MsgEntity;

import java.util.List;


/**
 * 聊天列表
 */
public class ChatListMsgView extends Fragment {

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
        MsgListAdapter msgListAdapter = new MsgListAdapter(context);
        recyclerView.setAdapter(msgListAdapter);
        // 请求网络

        // 同步好友
        List<MsgEntity> msgList = MsgDao.getMsg();
        msgListAdapter.setDataList(msgList);


        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        return view;
    }

}
