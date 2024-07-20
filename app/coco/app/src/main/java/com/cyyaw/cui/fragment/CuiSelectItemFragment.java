package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;


/**
 * 上拉弹出层
 */
public class CuiSelectItemFragment extends CuiSelectItem {

    private static final String TAG = CuiSelectItemFragment.class.getName();


    public CuiSelectItemFragment(String label, String value, CuiSelectCallBack callBack, boolean select) {
        this.label = label;
        this.value = value;
        this.callBack = callBack;
        this.select = select;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_select_list_item, container, false);

        TextView tv = view.findViewById(R.id.cui_select_item_text);
        if (null != label) {
            tv.setText(label);
        }
        if (null != callBack) {
            view.setOnClickListener((View v) -> {
                callBack.select(view, label, value);
            });
        }
        select(select);
        return view;
    }

    public void select(boolean select) {
        if (view != null) {
            View selectView = view.findViewById(R.id.cui_select_item_select);
            if (select) {
                selectView.setVisibility(View.VISIBLE);
            } else {
                selectView.setVisibility(View.GONE);
            }
        }
    }

}
