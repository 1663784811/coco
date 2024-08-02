package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.MsgListAdapter;
import com.cyyaw.coco.dao.UserMsgDao;
import com.cyyaw.coco.dao.table.UserMsgEntity;
import com.cyyaw.cui.fragment.CuiEmptyFragment;

import java.util.Date;
import java.util.List;


/**
 * 聊天列表
 */
public class ChatListMsgFragment extends Fragment {

    private static final String TAG = ChatListMsgFragment.class.getName();

    private Fragment cuiEmpty = new CuiEmptyFragment();
    private MsgListAdapter msgListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list, container, false);
        //加载布局
        RecyclerView chatList = view.findViewById(R.id.chatList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        msgListAdapter = new MsgListAdapter(getContext());
        chatList.setAdapter(msgListAdapter);
        MyApplication.post(() -> {
            // 查数据库
            List<UserMsgEntity> list = UserMsgDao.getList();


        });
        return view;
    }


    public void receiveMsg(String fromId, String msg) {
        UserMsgEntity bean = new UserMsgEntity();
        bean.setTid(fromId);
        bean.setName("");
        bean.setFace("");
        bean.setUpdateTime(new Date());
        msgListAdapter.updateData(bean);
    }
}
