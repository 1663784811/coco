package com.cyyaw.coco.activity.print;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.dao.EquipmentDao;
import com.cyyaw.cui.fragment.CuiChatInputIconFragment;
import com.cyyaw.cui.fragment.CuiIconListFragment;
import com.cyyaw.cui.fragment.CuiIconListItemFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiPopupFragment;


/**
 * 打印输入页面
 */
public class PrintInputActivity extends AppCompatActivity {

    private static final String addressKey = "keyAddress";

    private String blueToothAddress;

    private CuiPopupFragment popup;


    public static void openActivity(Context context, String address) {
        Intent intent = new Intent(context, PrintInputActivity.class);
        intent.putExtra(addressKey, address);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_input);

        // 接收数据
        blueToothAddress = getIntent().getStringExtra(addressKey);
        // ====
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CuiNavBarFragment nav = new CuiNavBarFragment("输入打印内容", new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public void clickMore() {
                popup.show(true);
            }
        }, true, true);
        ft.add(R.id.header_title, nav);

        popup = new CuiPopupFragment();
        CuiIconListFragment iconList = new CuiIconListFragment();
        iconList.addItem(new CuiIconListItemFragment(R.drawable.cui_icon_delete_24, "删除", (View v) -> {
            EquipmentDao.deleteEquipment(blueToothAddress);
            MyApplication.toast("删除成功！");
            Intent intent = new Intent();
            intent.putExtra("delete", true);
            setResult(RESULT_OK, intent);
            finish();
        }));


        popup.addItem(iconList);
        ft.add(R.id.printInput, popup);
        ft.commit();
        // ====
        Button goToPrintBtn = findViewById(R.id.goToPrintBtn);
        EditText printEditText = findViewById(R.id.printEditText);

        goToPrintBtn.setOnClickListener((View v) -> {
            Editable text = printEditText.getText();
            PrintPreviewActivity.openActivity(PrintInputActivity.this, blueToothAddress, text.toString());
        });


    }
}