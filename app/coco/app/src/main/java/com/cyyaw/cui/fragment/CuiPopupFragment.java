package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
 * 上拉弹出层
 */
public class CuiPopupFragment extends Fragment {

    private static final String TAG = CuiPopupFragment.class.getName();

    private String title;

    private List<Fragment> itemList = new ArrayList<>();

    private View view;


    public CuiPopupFragment() {
    }

    public CuiPopupFragment(String title) {
        this.title = title;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_popup, container, false);
        view.setVisibility(View.GONE);
        View cuiPopupCover = view.findViewById(R.id.cui_popup_cover);
        CuiPopup cuiPopup = view.findViewById(R.id.cui_popup_content);
        View closeBtn = view.findViewById(R.id.cui_popup_close);
        if(null != title){
            TextView cuiPopupTitle = view.findViewById(R.id.cui_popup_title);
            cuiPopupTitle.setText(title);
        }
        // =================
        if (null != itemList && itemList.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < itemList.size(); i++) {
                ft.add(R.id.cui_popup_item_container, itemList.get(i));
            }
            ft.commit();
        }
        // =================
        cuiPopup.setOnScrollStateListener(new CuiPopup.OnScrollStateListener() {
            @Override
            public void onState(int state) {
                if (state > 0) {
                } else {
                    // view.setVisibility(View.GONE);
                }
            }
        });
        cuiPopupCover.setOnClickListener((View v) -> {
            show(false);
        });
        closeBtn.setOnClickListener((View v) -> {
            show(false);
        });
        return view;
    }

    public void show(boolean isShow) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void addItem(Fragment fragment) {
        itemList.add(fragment);
    }
}
