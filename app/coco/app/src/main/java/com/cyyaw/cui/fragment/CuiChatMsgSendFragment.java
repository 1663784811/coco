package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cyyaw.coco.R;


public class CuiChatMsgSendFragment extends Fragment {

    private CuiChatMsgFromCallBack callBack;

    private String userId;
    private String userName;
    private String face;
    private String content;


    public CuiChatMsgSendFragment(String userId, String userName, String face, String content) {
        this.userId = userId;
        this.userName = userName;
        this.face = face;
        this.content = content;
    }

    public CuiChatMsgSendFragment(CuiChatMsgFromCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_msg_send, container, false);
        ImageView imageView = view.findViewById(R.id.faceImageView);
        if (null != face) {
            Glide.with(view.getContext()).load(face).into(imageView);
        }
        if (null != content) {
            TextView contentText = view.findViewById(R.id.contentText);
            contentText.setText(content);
        }
        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatMsgFromCallBack {


    }


}