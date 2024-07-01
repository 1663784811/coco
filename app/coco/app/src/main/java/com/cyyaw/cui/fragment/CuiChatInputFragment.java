package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;

import java.util.ArrayList;
import java.util.List;


public class CuiChatInputFragment extends Fragment {

    private CuiChatInputCallBack callBack;

    private List<CuiChatInputIconFragment> iconList = new ArrayList<>();


    public CuiChatInputFragment() {
    }

    public CuiChatInputFragment(CuiChatInputCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_chat_input, container, false);
        if (null != iconList && iconList.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < iconList.size(); i++) {
                ft.add(R.id.cuiChatIconContainer, iconList.get(i));
            }
            ft.commit();
        }
        View cuiChatIconBox = view.findViewById(R.id.cui_chat_icon_box);
        View cuiMoreUse = view.findViewById(R.id.cui_moreUse);
        View cuiChatSendBtn = view.findViewById(R.id.cui_chat_sendBtn);
        View cuiChatAudioText = view.findViewById(R.id.cuiChatAudioText);
        View cuiSpeechIcon = view.findViewById(R.id.cuiSpeechIcon);
        View cuiExpressionContainer = view.findViewById(R.id.cuiExpressionContainer);
        View expressionIcon = view.findViewById(R.id.expressionIcon);


        cuiChatIconBox.setVisibility(View.GONE);
        cuiChatSendBtn.setVisibility(View.GONE);
        cuiChatAudioText.setVisibility(View.GONE);
        cuiExpressionContainer.setVisibility(View.GONE);


        cuiSpeechIcon.setOnClickListener((View v) -> {
            if (cuiChatAudioText.getVisibility() == View.VISIBLE) {
                cuiChatAudioText.setVisibility(View.GONE);
            } else {
                cuiChatAudioText.setVisibility(View.VISIBLE);
            }
        });

        cuiMoreUse.setOnClickListener((View v) -> {
            if (cuiChatIconBox.getVisibility() == View.VISIBLE) {
                cuiChatIconBox.setVisibility(View.GONE);
            } else {
                cuiChatIconBox.setVisibility(View.VISIBLE);
            }
        });

        expressionIcon.setOnClickListener((View v) -> {
            if (cuiExpressionContainer.getVisibility() == View.VISIBLE) {
                cuiExpressionContainer.setVisibility(View.GONE);
            } else {
                cuiExpressionContainer.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }


    public void addIcon(CuiChatInputIconFragment icon) {
        this.iconList.add(icon);
    }

    /**
     * 方法回调
     */
    public interface CuiChatInputCallBack {

        /**
         * 点击图标
         */
        default void clickIcon(View v) {
        }
    }


}