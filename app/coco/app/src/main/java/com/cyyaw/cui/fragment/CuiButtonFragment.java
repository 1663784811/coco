package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cyyaw.cui.view.CuiButton;

public class CuiButtonFragment extends Fragment {

    private String text;

    private View view;


    public CuiButtonFragment(String text) {
        this.text = text;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = new CuiButton(getContext());

        return view;
    }


}
