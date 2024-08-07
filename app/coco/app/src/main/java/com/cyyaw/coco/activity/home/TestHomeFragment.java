package com.cyyaw.coco.activity.home;

import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.cyyaw.coco.activity.home.adapter.HomeBluetoothListAdapter;
import com.cyyaw.coco.common.BaseAppCompatActivity;

public class TestHomeFragment extends LinearLayout {

    private static final String TAG = TestHomeFragment.class.getName();

    private BaseAppCompatActivity context;

    private HomeBluetoothListAdapter homeBluetoothListAdapter;

//    BlueToothReceiver br;


    public TestHomeFragment(BaseAppCompatActivity context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public TestHomeFragment(BaseAppCompatActivity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(null);
    }

    public TestHomeFragment(BaseAppCompatActivity context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(null);
    }


    private void initView(AttributeSet attrs) {
//        // =============== 加载布局
//        View viewHome = LayoutInflater.from(context).inflate(R.layout.activity_main_home, this, true);
//        // =============== 设置适配器
//        RecyclerView recyclerView = viewHome.findViewById(R.id.equipmentList);
//        LinearLayoutManager layoutManager = new LinearLayoutManagerNonScrollable(context);
//        recyclerView.setLayoutManager(layoutManager);
//        homeBluetoothListAdapter = new HomeBluetoothListAdapter(context);
//        recyclerView.setAdapter(homeBluetoothListAdapter);
//        // 开启服务
//        Intent intent = new Intent(context, BluetoothClassicService.class);
//        intent.putExtra("clazz", BroadcastEnum.ACTIVITY_HOME);
//        context.startService(intent);
//        // 注册广播
////        br = new BlueToothReceiver(context, this);
//
//        ActivityUtils.blueToothPermissions(context, () -> {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            BluetoothAdapter.getDefaultAdapter().startDiscovery();
//        });
    }


//    @Override
//    public void foundDev(BluetoothDevice dev, short rssi) {
//        BluetoothEntity bluetooth = new BluetoothEntity();
//        bluetooth.setName(dev.getName());
//        bluetooth.setAddress(dev.getAddress());
//        bluetooth.setType(dev.getType());
//        bluetooth.setRssi(rssi);
//        homeBluetoothListAdapter.updateData(bluetooth);
//        MyApplication.blueTooth.put(dev.getAddress(), dev);
//    }

}
