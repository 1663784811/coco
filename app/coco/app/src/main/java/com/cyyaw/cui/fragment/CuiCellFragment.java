package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;

public class CuiCellFragment extends Fragment {


    private String leftText;

    private CuiClickCallBack callBack;

    public CuiCellFragment(String leftText) {
        this(leftText, null);
    }

    public CuiCellFragment(String leftText, CuiClickCallBack callBack) {
        this.leftText = leftText;
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_cell, container, false);
        if (null != leftText) {
            TextView cuiCellText = view.findViewById(R.id.cuiCellText);
            cuiCellText.setText(leftText);
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
