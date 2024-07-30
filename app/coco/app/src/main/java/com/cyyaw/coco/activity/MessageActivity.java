package com.cyyaw.coco.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.UserMsgLogDao;
import com.cyyaw.coco.dao.table.UserMsgLogEntity;
import com.cyyaw.cui.fragment.CuiChatInputFragment;
import com.cyyaw.cui.fragment.CuiChatInputIconFragment;
import com.cyyaw.cui.fragment.CuiChatMsgFromFragment;
import com.cyyaw.cui.fragment.CuiChatMsgSendFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.webrtc.MsgCallBack;
import com.cyyaw.webrtc.WebRtcConfig;
import com.cyyaw.webrtc.activity.PhoneCallActivity;
import com.cyyaw.webrtc.net.SocketManager;

import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements CuiChatInputFragment.CuiChatInputCallBack, MsgCallBack {

    private static final String USERID = "userId";
    private static final String USERNAME = "userName";
    private static final String FACE = "face";

    private String userName;
    private String userId;
    private String face;

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
        userId = intent.getStringExtra(USERID);
        userName = intent.getStringExtra(USERNAME);
        face = intent.getStringExtra(FACE);

        ScrollView chatScrollView = findViewById(R.id.chatScrollView);

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
        // 点击发送数据
        cuiChatInputFragment.setSendDataCallBack((String data) -> {
            MyApplication.post(() -> {
                getSupportFragmentManager().beginTransaction().add(R.id.messageContent, new CuiChatMsgSendFragment(userId, userName, face, data)).commit();
            });
            // 发送
            SocketManager.getInstance().sendChatMsg(userId, data);
            chatScrollView.postDelayed(() -> {
                chatScrollView.fullScroll(View.FOCUS_DOWN);
            }, 100);
        });
        // 获取焦点
        cuiChatInputFragment.setFocusChangeListenerCallBack((View v, boolean hasFocus) -> {
            if (hasFocus) {
                chatScrollView.postDelayed(() -> {
                    chatScrollView.fullScroll(View.FOCUS_DOWN);
                }, 100);
            }
        });
        trs.add(R.id.chat_input, cuiChatInputFragment);
        trs.commit();
        chatScrollView.postDelayed(() -> {
            chatScrollView.fullScroll(View.FOCUS_DOWN);
        }, 50);

        SocketManager.getInstance().setMsgChatCallBack(userId, this);

        UserMsgLogDao.getMsgLog(userId, "xxx", new Date());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().setMsgChatCallBack(null, null);
    }

    @Override
    public void receiveMsg(String fromId, String msg) {
        getSupportFragmentManager().beginTransaction().add(R.id.messageContent, new CuiChatMsgFromFragment(fromId, userName, face, msg)).commit();
    }


}