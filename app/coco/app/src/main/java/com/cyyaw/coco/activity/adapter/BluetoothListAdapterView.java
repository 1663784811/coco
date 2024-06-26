package com.cyyaw.coco.activity.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyyaw.coco.R;
import com.cyyaw.bluetooth.device.BlueTooth;

public class BluetoothListAdapterView extends RecyclerView.ViewHolder {


    private View itemView;
    private ScanResult scanResult;

    public BluetoothListAdapterView(@NonNull View itemView, BlueTooth blueTooth) {
        super(itemView);
        this.itemView = itemView;
        itemView.findViewById(R.id.connectBtn).setOnClickListener((View v) -> {
            BluetoothDevice device = scanResult.getDevice();
            String address = device.getAddress();
            ((Button) v).setText("连接中...");

        });
    }

    @SuppressLint({"SetTextI18n", "MissingPermission"})
    public void setDAta(ScanResult scanResult) {
        this.scanResult = scanResult;
        BluetoothDevice device = scanResult.getDevice();
        TextView eq = itemView.findViewById(R.id.equipmentName);
        eq.setText(device.getName());
        TextView equipmentType = itemView.findViewById(R.id.equipmentType);
        equipmentType.setText(device.getType() + "");
        TextView equipmentAddress = itemView.findViewById(R.id.equipmentAddress);
        equipmentAddress.setText(device.getAddress());
        TextView equipmentSignal = itemView.findViewById(R.id.equipmentSignal);
        equipmentSignal.setText(scanResult.getRssi() + "");
    }
}
