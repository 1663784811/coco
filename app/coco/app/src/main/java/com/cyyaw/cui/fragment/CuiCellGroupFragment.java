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

public class CuiCellGroupFragment extends Fragment {


    private List<Fragment> cellList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_cell_group, container, false);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (int i = 0; i < cellList.size(); i++) {
            ft.add(R.id.cuiCellGroup, cellList.get(i));
            if (cellList.size() > i + 1) {
                // ToDo 画线
            }
        }
        ft.commit();
        return view;
    }

    public void addCell(Fragment cell) {
        cellList.add(cell);
    }
}
