package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;
import com.cyyaw.cui.view.CuiPopup;


/**
 * 上拉弹出层
 */
public class CuiPopupFragment extends Fragment {

    private static final String TAG = CuiPopupFragment.class.getName();

    private String title;

    public CuiPopupFragment(String title){
        this.title = title;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_popup, container, false);
        View cuiPopupCover = view.findViewById(R.id.cui_popup_cover);
        CuiPopup cuiPopup = view.findViewById(R.id.cui_popup_content);
        View closeBtn = view.findViewById(R.id.cui_popup_close);
        TextView cuiPopupTitle = view.findViewById(R.id.cui_popup_title);


        cuiPopupTitle.setText(title);

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
            Log.e(TAG, "onCreateView: ssssssssssssssssssssssssssssssssssss");
            cuiPopup.toBottom();
        });
        closeBtn.setOnClickListener((View v) -> {
            Log.e(TAG, "onCreateView: ");
            cuiPopup.toBottom();
        });

        return view;
    }


}
