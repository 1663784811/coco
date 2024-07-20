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
import com.cyyaw.cui.view.CuiPopup;

import java.util.ArrayList;
import java.util.List;


/**
 * icon列表
 */
public class CuiIconListFragment extends Fragment {

    private List<Fragment> itemList = new ArrayList<>();
    private View view;
    public CuiIconListFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_icon_list, container, false);
        // =================
        if (null != itemList && itemList.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < itemList.size(); i++) {
                ft.add(R.id.cui_icon_container, itemList.get(i));
            }
            ft.commit();
        }
        // =================
        return view;
    }



    public void addItem(Fragment fragment) {
        itemList.add(fragment);
    }
}
