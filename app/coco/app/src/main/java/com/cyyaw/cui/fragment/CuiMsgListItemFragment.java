package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;

public class CuiMsgListItemFragment extends Fragment {

    private View view;
    // 头像
    private String face;
    // 名称
    private String userName;
    // 消息
    private String msg;

    public CuiMsgListItemFragment(View view, String face, String userName, String msg) {
        this.view = view;
        this.face = face;
        this.userName = userName;
        this.msg = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_msg_list_item, container, false);

        view.findViewById(R.id.cui_face);


        return view;
    }




}
