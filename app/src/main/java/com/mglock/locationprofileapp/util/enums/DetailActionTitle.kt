package com.mglock.locationprofileapp.util.enums

enum class DetailActionTitle(val title: String){
    CHANGE_VOLUME("Change Volume"){
        override fun getRequiredPermissions(): List<String> {
            TODO("Not yet implemented")
        }
    },
    CHANGE_RINGTONE("Change Ringtone"){
        override fun getRequiredPermissions(): List<String> {
            TODO("Not yet implemented")
        }
    },
    CHANGE_VOLUME_MODE("Change Volume Mode"){
        override fun getRequiredPermissions(): List<String> {
            TODO("Not yet implemented")
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
}