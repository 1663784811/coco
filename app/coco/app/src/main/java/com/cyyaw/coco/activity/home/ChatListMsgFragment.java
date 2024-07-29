package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.coco.entity.MsgUserEntity;
import com.cyyaw.cui.fragment.CuiEmptyFragment;
import com.cyyaw.cui.fragment.CuiMsgListItemFragment;
import com.cyyaw.cui.fragment.entity.CuiMsgEntity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 聊天列表
 */
public class ChatListMsgFragment extends Fragment {

    private Fragment cuiEmpty = new CuiEmptyFragment();

    private List<MsgUserEntity> msgUserList = new CopyOnWriteArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list, container, false);
        //加载布局
        LinearLayout linearLayout = view.findViewById(R.id.chatList);


        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.chatList, cuiEmpty);

        // 查数据库


        ft.commit();


        return view;
    }


    public synchronized void aaa(MsgUserEntity obj) {
        String tid = obj.getTid();
        CuiMsgEntity nowData = new CuiMsgEntity();
        nowData.setFace("ssss");
        nowData.setUserName("ddd");
        nowData.setMsg("ddddd");
        nowData.setNumber(0);

        // 查当前位置
        int index = -1;
        for (int i = 0; i < msgUserList.size(); i++) {
            MsgUserEntity msgUserEntity = msgUserList.get(i);
            String idStr = msgUserEntity.getTid();
            if (tid.equals(idStr)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            // 没有则插入
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            CuiMsgListItemFragment fragment = new CuiMsgListItemFragment(nowData);
            ft.add(R.id.chatList, fragment);
            ft.commit();
            obj.setFragment(fragment);
            obj.setTid(tid);
            msgUserList.add(obj);
            index = msgUserList.size() - 1;
        }
        // 更新数据
        while (index > 0) {
            MsgUserEntity previous = msgUserList.get(index - 1);
            CuiMsgListItemFragment previousFragment = previous.getFragment();
            CuiMsgEntity data = previousFragment.getData();
            // 交换数据
            MsgUserEntity now = msgUserList.get(index);
            now.setTid(previous.getTid());
            CuiMsgListItemFragment nowFragment = now.getFragment();
            nowFragment.updateData(data);
            index--;
            if (index == 0) {
                MsgUserEntity first = msgUserList.get(index);
                first.setTid(tid);
                first.getFragment().updateData(nowData);
            }
        }
    }


}
