package com.mglock.locationprofileapp.util.enums

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.mglock.locationprofileapp.views.profiles.fragments.AddActionRingtoneFragment
import com.mglock.locationprofileapp.views.profiles.fragments.AddActionVolumeModeFragment
import com.mglock.locationprofileapp.views.profiles.fragments.AddActionVolumeFragment

enum class DetailActionOption(val title: String){
    CHANGE_VOLUME("Change Volume"){
        override fun getRequiredPermissions(): List<String> {
            return emptyList()
        }
    },
    CHANGE_RINGTONE("Change Ringtone"){
        override fun getRequiredPermissions(): List<String> {
            return emptyList()
        }
    },
    CHANGE_VOLUME_MODE("Change Volume Mode"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
        }
    },
    NOTIFY_BLUETOOTH_ENABLED("Bluetooth state notification"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(Manifest.permission.BLUETOOTH)
        }
    },
    NOTIFY_BLUETOOTH_DEVICE_CONNECTED("Bluetooth connected to Device"){
        @RequiresApi(Build.VERSION_CODES.S)
        override fun getRequiredPermissions(): List<String> {
            return listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        }
    },
    NOTIFY_WIFI_ENABLED("Wi-Fi state notification"){
        override fun getRequiredPermissions(): List<String> {
            TODO("Not yet implemented")
        }
    };

    abstract fun getRequiredPermissions(): List<String>

    companion object{
        fun getValueSelectionFragment(enum: DetailActionOption): Fragment{
            return when(enum){
                CHANGE_VOLUME_MODE -> AddActionVolumeModeFragment()
                CHANGE_VOLUME -> AddActionVolumeFragment()
                CHANGE_RINGTONE -> AddActionRingtoneFragment()
                NOTIFY_BLUETOOTH_ENABLED -> Fragment()
                NOTIFY_BLUETOOTH_DEVICE_CONNECTED -> Fragment()
                else -> Fragment()
            }
        }
    }
}