package com.cyyaw.cui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 上拉弹出层
 */
public class CuiSelectListFragment extends Fragment implements CuiSelectCallBack {

    private static final String TAG = CuiSelectListFragment.class.getName();

    private final Map<String, CuiSelectItemEntity> itemMap = new ConcurrentHashMap<>();

    protected CuiSelectCallBack callBack;

    private View view;


    public CuiSelectListFragment(CuiSelectCallBack callBack) {
        this.callBack = callBack;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cui_select_list, container, false);
        if (null != itemMap && itemMap.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (String label : itemMap.keySet()) {
                CuiSelectItemEntity bean = itemMap.get(label);
                ft.add(R.id.cui_select_list_container, bean.getItem());
            }
            ft.commit();
        }
        return view;
    }

    public void addItem(String label, String value) {
        if (null != label) {
            CuiSelectItemEntity bean = new CuiSelectItemEntity();
            bean.setLabel(label);
            bean.setValue(value);
            bean.setItem(new CuiSelectItemFragment(label, value, this, false));
            itemMap.put(label, bean);
            if (null != view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.add(R.id.cui_select_list_container, bean.getItem());
                ft.commit();
            }
        }
    }

    @Override
    public void select(View v, String label, String value) {
        // 选中效果
        for (String key : itemMap.keySet()) {
            CuiSelectItemEntity cuiSelectItemEntity = itemMap.get(key);
            if (label.equals(key)) {
                cuiSelectItemEntity.getItem().select(true);
            } else {
                cuiSelectItemEntity.getItem().select(false);
            }
        }
        if (null != callBack) {
            callBack.select(v, label, value);
        }
    }


    private class CuiSelectItemEntity {
        private String label;
        private String value;
        private CuiSelectItemFragment item;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public CuiSelectItemFragment getItem() {
            return item;
        }

        public void setItem(CuiSelectItemFragment item) {
            this.item = item;
        }
    }

}
