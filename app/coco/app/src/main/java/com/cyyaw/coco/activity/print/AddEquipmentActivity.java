package com.cyyaw.coco.activity.print;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.out.BlueToothFindCallBack;
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
import com.cyyaw.cui.fragment.CuiSelectListFragment;
import com.cyyaw.cui.fragment.callback.CuiSelectCallBack;
import com.cyyaw.cui.window.CuiPopWindow;

import java.util.Map;

/**
 * 添加设备
 */
public class AddEquipmentActivity extends BaseAppCompatActivity implements CuiSelectCallBack {

    private final String TAG = AddEquipmentActivity.class.getName();

    private BluetoothAdapter bluetoothAdapter;

    private CuiSelectListFragment selectList;

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
            public void clickMore(View v) {


            }
        }, true, true);
        ft.add(R.id.header_title, nav);


        CuiCellGroupFragment group = new CuiCellGroupFragment();
        group.addCell(new CuiInputFragment("名称", (View v, String data) -> {
            name = data;
        }));

        group.addCell(new CuiInputFragment("类型", (View v, String data) -> {
            address = data;
        }));

        group.addCell(new CuiCellFragment("选择设备", (View v) -> {
            popup.show(true);
        }));


        ft.add(R.id.inputContainer, group);
        popup = new CuiPopupFragment("请选择蓝牙设备");

        selectList = new CuiSelectListFragment(this);
        Map<String, BtEntity> bluetoothMap = BlueToothManager.getBluetoothMap();
        if (null != bluetoothMap) {
            for (String key : bluetoothMap.keySet()) {
                BtEntity btEntity = bluetoothMap.get(key);
                BluetoothDevice dev = btEntity.getDev();
                String address = dev.getAddress();
                String btName = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    btName = dev.getAddress();
                } else {
                    btName = dev.getName();
                }
                selectList.addItem(btName == null ? address : btName, address);
            }
        }
        ft.add(R.id.inputContainer, selectList);
//        popup.addItem(selectList);

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


//        View searchBluetooth = findViewById(R.id.search_bluetooth);
//        searchBluetooth.setOnClickListener((View v) -> {
//            scanLeDevice();
//        });
    }


    @Override
    public void onStart() {
        super.onStart();


    }


    private void scanLeDevice() {

        BlueToothManager.setCallBack(new BlueToothFindCallBack() {
            @Override
            public void error() {

            }

            @Override
            public void foundBluetooth(BtEntity bluetooth) {
                MyApplication.post(() -> {
                    BluetoothDevice dev = bluetooth.getDev();
                    String ads = dev.getAddress();
                    String btName = null;
                    if (ActivityCompat.checkSelfPermission(AddEquipmentActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        btName = dev.getAddress();
                    } else {
                        btName = dev.getName();
                    }
                    Log.e(TAG, "foundBluetooth : " + ads);
                    selectList.addItem(btName == null ? address : btName, address);
                });
            }
        });

        BlueToothManager.discoveryBlueTooth();
    }

    @Override
    public void select(View v, String label, String value) {
        address = value;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlueToothManager.setCallBack(null);
    }
}