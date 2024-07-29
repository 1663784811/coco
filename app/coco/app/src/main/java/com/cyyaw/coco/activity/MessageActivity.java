package com.cyyaw.coco.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiChatInputFragment;
import com.cyyaw.cui.fragment.CuiChatInputIconFragment;
import com.cyyaw.cui.fragment.CuiChatMsgFromFragment;
import com.cyyaw.cui.fragment.CuiChatMsgSendFragment;
import com.cyyaw.webrtc.activity.PhoneCallActivity;

public class MessageActivity extends AppCompatActivity implements CuiChatInputFragment.CuiChatInputCallBack {

    private static final String USERID = "userId";
    private static final String USERNAME = "userName";
    private static final String FACE = "face";


    public static void openActivity(Context context, String userId, String userName, String face) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(USERID, userId);
        intent.putExtra(USERNAME, userName);
        intent.putExtra(FACE, face);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        String userId = intent.getStringExtra(USERID);
        String userName = intent.getStringExtra(USERNAME);
        String face = intent.getStringExtra(FACE);


        FragmentTransaction trs = getSupportFragmentManager().beginTransaction();
        // fragment
        CuiNavBarFragment navBar = new CuiNavBarFragment(userName, new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public boolean clickBack(View v) {
                return false;
            }
        }, true, false);

        trs.add(R.id.header_bar, navBar);

        CuiChatInputFragment cuiChatInputFragment = new CuiChatInputFragment();
        cuiChatInputFragment.addIcon(new CuiChatInputIconFragment(R.drawable.cui_icon_video_24, "图片", (View v) -> {


        }));
        cuiChatInputFragment.addIcon(new CuiChatInputIconFragment(R.drawable.cui_icon_video_24, "拍摄", (View v) -> {


        }));
        cuiChatInputFragment.addIcon(new CuiChatInputIconFragment(R.drawable.cui_icon_video_24, "语音通话", (View v) -> {


        }));
        cuiChatInputFragment.addIcon(new CuiChatInputIconFragment(R.drawable.cui_icon_video_24, "视频通话", (View v) -> {
            PhoneCallActivity.openActivity(MessageActivity.this, userId, true, "22", false, false);
        }));
        trs.add(R.id.chat_input, cuiChatInputFragment);

        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.add(R.id.messageContent, new CuiChatMsgFromFragment(userId, userName, face));
        trs.commit();
    }

}