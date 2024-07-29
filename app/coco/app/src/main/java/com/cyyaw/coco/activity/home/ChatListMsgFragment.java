package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.UserMsgDao;
import com.cyyaw.coco.dao.table.UserMsgEntity;
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

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.chatList, cuiEmpty);
        ft.commit();

        MyApplication.post(() -> {
            // 查数据库
            List<UserMsgEntity> list = UserMsgDao.getList();
            for (int i = (list.size() - 1); i > 0; i--) {
                UserMsgEntity msg = list.get(i);
                CuiMsgEntity cuiMsgEntity = new CuiMsgEntity();
                cuiMsgEntity.setFace(msg.getFace());
                cuiMsgEntity.setUserName(msg.getName());
                cuiMsgEntity.setMsg("");
                cuiMsgEntity.setNumber(0);
                String tid = msg.getTid();
                showToPage(tid, cuiMsgEntity);
            }
        });
        return view;
    }


    /**
     * 消息显示到页面
     */
    public void showToPage(String tid, CuiMsgEntity nowData) {
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
            MsgUserEntity obj = new MsgUserEntity();
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
        if (cuiEmpty != null) {
            getChildFragmentManager().beginTransaction().remove(cuiEmpty).commit();
        }
    }


}
