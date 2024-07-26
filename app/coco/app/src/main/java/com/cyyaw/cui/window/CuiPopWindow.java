package com.cyyaw.cui.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;

import com.cyyaw.coco.R;

public class CuiPopWindow {


    private View view;

    private PopupWindow popupWindow;

    private CuiPopWindow(Context context, @LayoutRes int resource) {
        view = LayoutInflater.from(context).inflate(resource, null, false);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.anim.cui_anim_pop);
        popupWindow.setTouchable(true);
    }


    public static CuiPopWindow getWindow(Context context, @LayoutRes int resource) {
        return new CuiPopWindow(context, resource);
    }

    public CuiPopWindow show(View v, int x, int y) {
        popupWindow.showAsDropDown(v, x, y);
        return this;
    }

    public View getView() {
        return this.view;
    }

}
