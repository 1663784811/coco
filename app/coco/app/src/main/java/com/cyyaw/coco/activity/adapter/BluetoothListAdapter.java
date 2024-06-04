package com.cyyaw.coco.activity.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("MissingPermission")
public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.ViewHolder> {


    private final Context context;

    private List<BluetoothDevice> blueToothList = new ArrayList<>();


    public BluetoothListAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public BluetoothListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_item, parent, false);
        Button btn = view.findViewById(R.id.connectBtn);
        btn.setOnClickListener(this::connectBtn);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDAta(blueToothList.get(position));
    }


    @Override
    public int getItemCount() {
        return blueToothList.size();
    }


    public void setBlueTooth(BluetoothDevice device) {
        String name = device.getName();
        String address = device.getAddress();
        boolean h = false;
        for (int i = 0; i < blueToothList.size(); i++) {
            BluetoothDevice bluetoothDevice = blueToothList.get(i);
            String ad = bluetoothDevice.getAddress();
            if (ad.equals(address)) {
                h = true;
                blueToothList.set(i, device);
            }
        }
        if (!h) {
            blueToothList.add(device);
        }
        System.out.println("蓝牙名" + name);
    }

    /**
     * 连接蓝牙
     */
    private void connectBtn(View view) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void setDAta(BluetoothDevice bluetoothDevice) {


        }
    }

}
