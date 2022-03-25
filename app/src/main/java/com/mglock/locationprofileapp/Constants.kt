package com.mglock.locationprofileapp

class Constants {

    enum class ActionGroupTitle(val title: String){
        VOLUME("Volume"),
        WIFI("Wi-Fi"),
        MOBILE_DATA("Mobile Data"),
        SCREEN_BRIGHTNESS("Screen Brightness"),
        NOTIFICATIONS("Notifications"),
        LOCATION("Location"),
        BLUETOOTH("Bluetooth")
    }

    companion object{
        val tabTitles = arrayOf(
            "Actions",
            "Profiles",
            "Places"
        )
    }
}