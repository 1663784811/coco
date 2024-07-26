package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CuiTextFragment extends Fragment {

    private String text;
    private TextView textView;

    public CuiTextFragment(String text) {
        this.text = text;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(0xFFFFFFFF);
        textView.setTextSize(16);
        textView.setPadding(16, 0, 16, 0);
        return textView;
    }


    public TextView getTextView() {
        return textView;
    }


}

