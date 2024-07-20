package com.cyyaw.coco.activity.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.MyApplication;
import com.cyyaw.coco.R;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.coco.dao.EquipmentDao;
import com.cyyaw.coco.dao.table.EquipmentEntity;
import com.cyyaw.cui.fragment.CuiCellFragment;
import com.cyyaw.cui.fragment.CuiCellGroupFragment;
import com.cyyaw.cui.fragment.CuiInputFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.fragment.CuiPopupFragment;
import com.cyyaw.cui.fragment.CuiSelectItemFragment;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;

import java.util.Map;

/**
 * 添加设备
 */
public class AddEquipmentActivity extends BaseAppCompatActivity implements CuiSelectCallBack {

    private final String TAG = AddEquipmentActivity.class.getName();

    private BluetoothAdapter bluetoothAdapter;

    private CuiPopupFragment popup;

    private String name;
    private String address;


    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AddEquipmentActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        CuiNavBarFragment nav = new CuiNavBarFragment("添加设备", new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public void clickMore() {

            }
        }, true, true);
        ft.add(R.id.header_title, nav);


        CuiCellGroupFragment group = new CuiCellGroupFragment();
        group.addCell(new CuiInputFragment("名称", (View v, String data) -> {
            name = data;
        }));

        group.addCell(new CuiInputFragment("地址", (View v, String data) -> {
            address = data;
        }));

        group.addCell(new CuiCellFragment("选择设备", (View v) -> {
            popup.show(true);
        }));


        ft.add(R.id.inputContainer, group);
        popup = new CuiPopupFragment("请选择蓝牙设备");


        Map<String, BtEntity> bluetoothMap = BlueToothManager.getBluetoothMap();
        if (null != bluetoothMap) {
            for (String key : bluetoothMap.keySet()) {
                BtEntity btEntity = bluetoothMap.get(key);
                BluetoothDevice dev = btEntity.getDev();
                String address = dev.getAddress();
                popup.addItem(new CuiSelectItemFragment(address, address, this));
            }
        }

        ft.add(R.id.add_equipment, popup);
        ft.commit();

        View addEquipmentBtn = findViewById(R.id.addEquipmentBtn);
        addEquipmentBtn.setOnClickListener((View v) -> {
            EquipmentEntity data = new EquipmentEntity();
            data.setType(0);
            data.setName(name);
            data.setAddress(address);
            data.setImgUrl("");
            data.setStatus(0);
            EquipmentDao.addEquipment(data);

            finish();
        });
        // 打开蓝牙、搜索
        requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                // 搜索蓝牙
                scanLeDevice();
            });
        });


    }


    private void scanLeDevice() {

        BlueToothManager.setCallBack(new BlueToothCallBack() {
            @Override
            public void error() {

            }

            @Override
            public void foundBluetooth(BtEntity bluetooth) {
                Log.e(TAG, "foundBluetooth: " + bluetooth.getDev().getAddress());
            }
        });

        BlueToothManager.discoveryBlueTooth();
    }

    @Override
    public void select(View v, String data) {
        Log.e(TAG, "select: " + data);
        address = data;
    }
}