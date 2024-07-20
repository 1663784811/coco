package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;

import java.util.ArrayList;
import java.util.List;


/**
 * 上拉弹出层
 */
public class CuiSelectListFragment extends Fragment {

    private static final String TAG = CuiSelectListFragment.class.getName();

    private List<CuiSelectItem> itemList = new ArrayList<>();

    protected CuiSelectCallBack callBack;

    private View view;


    public CuiSelectListFragment(CuiSelectCallBack callBack) {
        this.callBack = callBack;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_select_list, container, false);
        if (null != itemList && itemList.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < itemList.size(); i++) {
                ft.add(R.id.cui_select_list_container, itemList.get(i));
            }
            ft.commit();
        }
        return view;
    }

    public void addItem(CuiSelectItem fragment) {
        itemList.add(fragment);
        if (null != view) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.cui_select_list_container, fragment);
            ft.commit();
        }
    }

}
