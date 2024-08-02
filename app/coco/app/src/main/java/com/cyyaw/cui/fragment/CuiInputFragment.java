package com.cyyaw.cui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.callback.CuiChangeCallBack;
import com.cyyaw.cui.fragment.callback.CuiClickCallBack;

public class CuiInputFragment extends Fragment {


    /**
     * 标题
     */
    private String name;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 点击回调
     */
    private CuiChangeCallBack callBack;

    public CuiInputFragment(String name) {
        this(name, null);
    }

    public CuiInputFragment(String name, CuiChangeCallBack callBack) {
        this(name, callBack, null);
    }

    public CuiInputFragment(String name, CuiChangeCallBack callBack, String message) {
        this.name = name;
        this.callBack = callBack;
        this.message = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cui_input, container, false);
        EditText cuiInputText = view.findViewById(R.id.cui_input_text);

        TextView cuiInputName = view.findViewById(R.id.cui_input_name);
        if (null != name) {
            cuiInputName.setText(name);
        }

        if (null != callBack) {
            cuiInputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = cuiInputText.getText().toString();
                    callBack.change(cuiInputText, str);
                }
            });
        }
        return view;
    }


}
