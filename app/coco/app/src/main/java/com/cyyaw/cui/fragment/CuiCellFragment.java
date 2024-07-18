package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiClickCallBack;

public class CuiCellFragment extends Fragment {


    /**
     * 标题
     */
    private String leftText;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 点击回调
     */
    private CuiClickCallBack callBack;

    public CuiCellFragment(String leftText) {
        this(leftText, null);
    }

    public CuiCellFragment(String leftText, CuiClickCallBack callBack) {
        this(leftText, callBack, null);
    }

    public CuiCellFragment(String leftText, CuiClickCallBack callBack, String message) {
        this.leftText = leftText;
        this.callBack = callBack;
        this.message = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_cell, container, false);
        if (null != leftText) {
            TextView cuiCellText = view.findViewById(R.id.cuiCellText);
            cuiCellText.setText(leftText);
        }
        TextView cuiNoteText = view.findViewById(R.id.cuiNoteText);
        if (null != message) {
            cuiNoteText.setText(message);
        } else {
            cuiNoteText.setVisibility(View.GONE);
        }
        if (null != callBack) {
            View cuiCellContent = view.findViewById(R.id.cuiCellContent);
            cuiCellContent.setOnClickListener((View v) -> {
                callBack.clickIcon(v);
            });
        }
        return view;
    }


}
