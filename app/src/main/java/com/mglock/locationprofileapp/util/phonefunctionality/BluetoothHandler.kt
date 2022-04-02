package com.mglock.locationprofileapp.util.phonefunctionality

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class BluetoothHandler(context: Context): BaseHandler {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    // will be false unless permissions are granted
    private var isBluetoothEnabled: Boolean = false

    init {
        Dexter.withContext(context)
            .withPermissions(
                DetailActionOption.NOTIFY_BLUETOOTH_DEVICE_CONNECTED.getRequiredPermissions()
            ).withListener(PermissionListener(context){
                isBluetoothEnabled = bluetoothManager.adapter.isEnabled
            })
            .check()
    }

    // get information
    @SuppressLint("MissingPermission")
    fun getBondedBluetoothDevices(): MutableSet<BluetoothDevice>?{
        return if(isBluetoothEnabled){
            val adapter = bluetoothManager.adapter
            adapter.bondedDevices
        } else {
            null
        }
    }

    @SuppressLint("MissingPermission")
    fun getBondedBluetoothDeviceNames(): List<String>{
        val bondedDevices = getBondedBluetoothDevices()
        val bondedDeviceNames = bondedDevices?.map{ device -> device.name }

        return bondedDeviceNames ?: emptyList()
    }

    // actions
    fun isConnectedToDevice(): Boolean{
        //TODO
        return false
    }

    fun checkIfBluetoothEnabled(): Boolean{
        return isBluetoothEnabled
    }

    override fun executeTask(option: DetailActionOption, optionValue: String, optionMode: String?) {
        TODO("Not yet implemented")
    }

}