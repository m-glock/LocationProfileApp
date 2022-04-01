package com.mglock.locationprofileapp.util.enums

import android.Manifest
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
    WIFI("Wi-Fi"){
        override fun getRequiredPermissions(): List<String> {
            TODO("Not yet implemented")
        }
    },
    BLUETOOTH("Bluetooth"){
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
                else -> Fragment()
            }
        }
    }
}