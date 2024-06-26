package com.cyyaw.coco.activity.adapter;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.bluetooth.device.BlueTooth;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapterView> {


    private final Context context;
    private List<ScanResult> scanResultList = new ArrayList<>();

    private BlueTooth blueTooth;

    /**
     * 广播接收器
     */
    private final BroadcastReceiver gattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
//                // connected = true;
//            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
//                // connected = false;
//            }
        }
    };


    public BluetoothListAdapter(Context context, BlueTooth blueTooth) {
        this.context = context;
        this.blueTooth = blueTooth;
        // ==================== 注册广播接收器
        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothBleService.ACTION_GATT_CONNECTED);
//        intentFilter.addAction(BluetoothBleService.ACTION_GATT_DISCONNECTED);
//         注册广播
//        context.registerReceiver(gattUpdateReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);


    }


    @NonNull
    @Override
    public BluetoothListAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BluetoothListAdapterView(LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_item, parent, false), blueTooth);
    }


    @Override
    public void onBindViewHolder(@NonNull BluetoothListAdapterView holder, int position) {
        holder.setDAta(scanResultList.get(position));
    }


    @Override
    public int getItemCount() {
        return scanResultList.size();
    }


    public void setBlueTooth(ScanResult result) {
        BluetoothDevice device = result.getDevice();
        String name = device.getName();
        String address = device.getAddress();
        boolean h = false;
        for (int i = 0; i < scanResultList.size(); i++) {
            BluetoothDevice bluetoothDevice = scanResultList.get(i).getDevice();
            String ad = bluetoothDevice.getAddress();
            if (ad.equals(address)) {
                h = true;
                scanResultList.set(i, result);
                notifyDataSetChanged();
            }
        }
        if (!h) {
            scanResultList.add(result);
            notifyItemRangeInserted(scanResultList.size() - 1, 1);
        }
    }

}
