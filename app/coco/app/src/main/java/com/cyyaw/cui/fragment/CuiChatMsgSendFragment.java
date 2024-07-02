package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cyyaw.coco.R;


public class CuiChatMsgSendFragment extends Fragment {

    private CuiChatMsgFromCallBack callBack;

    private String userId;
    private String userName;
    private String face;


    public CuiChatMsgSendFragment(String userId, String userName, String face) {
        this.userId = userId;
        this.userName = userName;
        this.face = face;
    }

    public CuiChatMsgSendFragment(CuiChatMsgFromCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_msg_send, container, false);
        ImageView imageView = view.findViewById(R.id.faceImage);
        if (null != face) {
            Glide.with(view.getContext()).load(face).into(imageView);
        }
        return view;
    }


    /**
     * 方法回调
     */
    public interface CuiChatMsgFromCallBack {


    }


}