package com.mglock.locationprofileapp.util.enums

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import com.mglock.locationprofileapp.util.phonefunctionality.*
import com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments.*

enum class DetailActionOption(val title: String){
    CHANGE_VOLUME("Change Volume"){
        override fun getRequiredPermissions(): List<String> {
            return emptyList()
        }
        override fun getInfoText(): String {
            return "You can set the Volume of media, system or ringtone to a specific volume."
        }
    },
    CHANGE_RINGTONE("Change Ringtone"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(Manifest.permission.WRITE_SETTINGS)
        }
        override fun getInfoText(): String {
            return "You can change the ringtone of call, notification, or alarm to a specific ringtone."
        }
    },
    CHANGE_VOLUME_MODE("Change Volume Mode"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
        }
        override fun getInfoText(): String {
            return "You can switch your phone to normal, silent, or vibrate mode."
        }
    },
    NOTIFY_BLUETOOTH_ENABLED("Check Bluetooth state"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(Manifest.permission.BLUETOOTH)
        }
        override fun getInfoText(): String {
            return "You can get a notification whether bluetooth is enabled or disabled."
        }
    },
    NOTIFY_WIFI_ENABLED("Wi-Fi state notification"){
        override fun getRequiredPermissions(): List<String> {
            return emptyList()
        }
        override fun getInfoText(): String {
            return "You can get a notification whether wifi is enabled or disabled."
        }
    },
    NOTIFY_LOCATION_ENTERED_EXITED("Get notification"){
        override fun getRequiredPermissions(): List<String> {
            return listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        override fun getInfoText(): String {
            return "You can get a notification which a custom text."
        }
    };

    abstract fun getRequiredPermissions(): List<String>
    abstract fun getInfoText(): String

    companion object{
        fun getValueSelectionFragment(enum: DetailActionOption): Fragment{
            return when(enum){
                CHANGE_VOLUME_MODE -> AddActionVolumeModeFragment()
                CHANGE_VOLUME -> AddActionVolumeFragment()
                CHANGE_RINGTONE -> AddActionRingtoneFragment()
                NOTIFY_BLUETOOTH_ENABLED, NOTIFY_WIFI_ENABLED -> AddActionBluetoothEnabledFragment()
                NOTIFY_LOCATION_ENTERED_EXITED -> AddActionNotificationFragment()
            }
        }

        fun delegateTaskToHandler(enum: DetailActionOption, optionValue: String, optionMode: String?, context: Context){
            val handler = when(enum){
                CHANGE_VOLUME_MODE,
                CHANGE_VOLUME,
                CHANGE_RINGTONE -> AudioHandler(context)

                NOTIFY_BLUETOOTH_ENABLED -> BluetoothHandler(context)

                NOTIFY_WIFI_ENABLED -> WifiHandler(context)

                NOTIFY_LOCATION_ENTERED_EXITED -> NotificationHandler(context)
            }
            handler.executeTask(enum, optionValue, optionMode)
        }
    }
}