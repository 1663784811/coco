package com.cyyaw.coco.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.CuiEmptyFragment;

import java.util.List;


/**
 * 聊天列表
 */
public class ChatListMsgView extends Fragment {

    private Fragment cuiEmpty = new CuiEmptyFragment();

    private List<Object> ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list, container, false);
        //加载布局
        LinearLayout linearLayout = view.findViewById(R.id.chatList);


        FragmentTransaction ft = getChildFragmentManager().beginTransaction();


        ft.add(R.id.chatList, cuiEmpty);

        ft.commit();


        return view;
    }

}
