package com.mglock.locationprofileapp.receiver

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

@SuppressLint("MissingPermission")
class BluetoothBroadcastReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            val action: String? = intent.action
            val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    as? BluetoothDevice

            if (BluetoothDevice.ACTION_FOUND == action) {
                Log.i("Device found", "found device: ${device?.name}")
            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                Log.i("Device connected", "found device: ${device?.name}")
            }
        }
    }
}