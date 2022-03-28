package com.mglock.locationprofileapp.util.enums

import android.Manifest

//TODO add all required permissions
enum class ActionGroupTitle(val title: String){
    VOLUME("Volume"){
        override fun getRequiredPermissions() = listOf("")
    },
    WIFI("Wi-Fi"){
        override fun getRequiredPermissions() = listOf("")
    },
    MOBILE_DATA("Mobile Data"){
        override fun getRequiredPermissions() = listOf("")
    },
    SCREEN_BRIGHTNESS("Screen Brightness"){
        override fun getRequiredPermissions() = listOf("")
    },
    NOTIFICATIONS("Notifications"){
        override fun getRequiredPermissions() = listOf("")
    },
    LOCATION("Location"){
        override fun getRequiredPermissions() = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    },
    BLUETOOTH("Bluetooth"){
        override fun getRequiredPermissions() = listOf("")
    };

    abstract fun getRequiredPermissions(): List<String>
}