package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyyaw.bluetooth.entity.BluetoothEntity;
import com.cyyaw.bluetooth.out.BlueToothCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.activity.home.adapter.StaggeredGridLayoutManagerNonScrollable;
import com.cyyaw.coco.activity.print.AddEquipmentActivity;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.cui.fragment.CuiNavBarFragment;

public class FindView extends Fragment {

    private static final String TAG = FindView.class.getName();

    private HomeBluetoothListAdapter homeBluetoothListAdapter;

    private BaseAppCompatActivity context;

    public FindView(BaseAppCompatActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // =============== 加载布局
        View view = inflater.inflate(R.layout.activity_main_find, container, false);

        CuiNavBarFragment nav = new CuiNavBarFragment("我的设备");
        getChildFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();

        // =============== 设置适配器
        RecyclerView recyclerView = view.findViewById(R.id.equipmentList);
        // 瀑布流
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManagerNonScrollable(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        homeBluetoothListAdapter = new HomeBluetoothListAdapter(context);
        recyclerView.setAdapter(homeBluetoothListAdapter);
        initBlueTooth();


        Button addEquipmentBtn = view.findViewById(R.id.addEquipmentBtn);
        addEquipmentBtn.setOnClickListener((View v) -> {
            AddEquipmentActivity.openActivity(context);
        });
        return view;
    }


    public void initBlueTooth() {
        context.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            context.requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                context.requestPermissionsFn(PermissionsCode.BLUETOOTH_SCAN, () -> {
                    BlueToothManager.getInstance().setCallBack(new BlueToothCallBack() {
                        @Override
                        public void error() {

                        }

                        @Override
                        public void foundBluetooth(BluetoothEntity bluetooth) {
                            com.cyyaw.coco.entity.BluetoothEntity bt = new com.cyyaw.coco.entity.BluetoothEntity();
                            bt.setName("sss:");
                            bt.setAddress(bluetooth.getDev().getAddress());
                            bt.setType(0);
                            bt.setRssi(0);
                            Log.e(TAG, "foundBluetooth: 发现蓝牙");
                            homeBluetoothListAdapter.updateData(bt);
                        }
                    });
                    BlueToothManager.getInstance().discoveryBlueTooth();
                });
            });
        });
    }


}
