package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyyaw.bluetooth.entity.BtEntity;
import com.cyyaw.bluetooth.out.BlueToothFindCallBack;
import com.cyyaw.bluetooth.out.BlueToothManager;
import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.home.adapter.EquipmentAdapter;
import com.cyyaw.coco.activity.home.adapter.StaggeredGridLayoutManagerNonScrollable;
import com.cyyaw.coco.activity.print.AddEquipmentActivity;
import com.cyyaw.coco.common.BaseAppCompatActivity;
import com.cyyaw.coco.common.permission.PermissionsCode;
import com.cyyaw.coco.dao.EquipmentDao;
import com.cyyaw.coco.dao.table.EquipmentEntity;
import com.cyyaw.cui.fragment.CuiEmptyFragment;
import com.cyyaw.cui.fragment.CuiNavBarFragment;
import com.cyyaw.cui.window.CuiPopWindow;

import java.util.List;

public class FindFragment extends Fragment {

    private static final String TAG = FindFragment.class.getName();

    private EquipmentAdapter equipmentAdapter;
    private BaseAppCompatActivity context;
    private LinearLayout equipmentContainer;
    private Fragment cuiEmpty;
    private CuiPopWindow window;

    public FindFragment(BaseAppCompatActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // =============== 加载布局
        View view = inflater.inflate(R.layout.activity_main_find, container, false);
        window = CuiPopWindow.getWindow(context, R.layout.activity_main_find_menu);
        View windowView = window.getView();
        windowView.findViewById(R.id.add_equipment).setOnClickListener((View v) -> {
            AddEquipmentActivity.openActivity(context);
        });
        CuiNavBarFragment nav = new CuiNavBarFragment("我的设备", new CuiNavBarFragment.UiNavBarFragmentCallBack() {
            @Override
            public void clickMore(View v) {
                window.show(v, 0, 0);
            }
        }, false, true);
        getChildFragmentManager().beginTransaction().add(R.id.header_title, nav).commit();

        // =============== 设置适配器
//        equipmentContainer = view.findViewById(R.id.equipmentContainer);
        RecyclerView recyclerView = view.findViewById(R.id.equipmentList);
        // 瀑布流
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        equipmentAdapter = new EquipmentAdapter(context);
        recyclerView.setAdapter(equipmentAdapter);
        initBlueTooth();
        initEquipmentList();

//        Button addEquipmentBtn = view.findViewById(R.id.addEquipmentBtn);
//        // 添加
//        addEquipmentBtn.setOnClickListener((View v) -> {
//            AddEquipmentActivity.openActivity(context);
//        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initEquipmentList();
    }

    /**
     * 初始化设备列表
     */
    public void initEquipmentList() {
        // 获取数据库数据
        List<EquipmentEntity> equipmentList = EquipmentDao.equipmentList();
        // 更新列表
        equipmentAdapter.setData(equipmentList);
        Log.e(TAG, "initEquipmentListsssssssssssssssssssss: " + equipmentList.size());
        if (equipmentList.size() > 0 && null != cuiEmpty) {
            getChildFragmentManager().beginTransaction().remove(cuiEmpty).commit();
            cuiEmpty = null;
        } else if (equipmentList == null || equipmentList.size() == 0) {
            if (cuiEmpty == null) {
                cuiEmpty = new CuiEmptyFragment();
//                getChildFragmentManager().beginTransaction().add(R.id.equipmentContainer, cuiEmpty).commit();
            }
        }
    }

    /**
     * 初始化蓝牙
     */
    public void initBlueTooth() {
        context.requestPermissionsFn(PermissionsCode.BLUETOOTH_CONNECT, () -> {
            context.requestPermissionsFn(PermissionsCode.BLUETOOTH_REQUEST_ENABLE, () -> {
                context.requestPermissionsFn(PermissionsCode.BLUETOOTH_SCAN, () -> {
                    BlueToothManager.setCallBack(new BlueToothFindCallBack() {
                        @Override
                        public void error() {

                        }

                        @Override
                        public void foundBluetooth(BtEntity bluetooth) {
                            equipmentAdapter.updateData(bluetooth);
                        }
                    });
                    BlueToothManager.discoveryBlueTooth();
                });
            });
        });
    }


}
