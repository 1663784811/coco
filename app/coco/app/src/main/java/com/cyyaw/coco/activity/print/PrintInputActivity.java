package com.cyyaw.coco.activity.print;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyyaw.coco.R;
import com.cyyaw.cui.fragment.CuiNavBarFragment;


/**
 * 打印输入页面
 */
public class PrintInputActivity extends AppCompatActivity {

    private static final String addressKey = "keyAddress";

    private String blueToothAddress;

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
        CuiNavBarFragment nav = new CuiNavBarFragment("输入打印内容");
        getSupportFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();
        // ====
        Button goToPrintBtn = findViewById(R.id.goToPrintBtn);
        EditText printEditText = findViewById(R.id.printEditText);

        goToPrintBtn.setOnClickListener((View v) -> {
            Editable text = printEditText.getText();
            PrintPreviewActivity.openActivity(PrintInputActivity.this, blueToothAddress, text.toString());
        });

    }
}