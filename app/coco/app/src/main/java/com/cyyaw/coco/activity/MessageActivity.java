package com.cyyaw.coco.activity;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.cui.fragment.CUiNavBarFragment;
import com.cyyaw.cui.fragment.CuiChatInputFragment;
import com.cyyaw.cui.fragment.CuiChatMsgFromFragment;
import com.cyyaw.cui.fragment.CuiChatMsgSendFragment;

public class MessageActivity extends BaseAppCompatActivity implements CuiChatInputFragment.CuiChatInputCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        FragmentTransaction trs = getSupportFragmentManager().beginTransaction();
        // fragment
        trs.add(R.id.header_bar, new CUiNavBarFragment());
        trs.add(R.id.chat_input, new CuiChatInputFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment());
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment());
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment());
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment());
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment());
        trs.commit();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}