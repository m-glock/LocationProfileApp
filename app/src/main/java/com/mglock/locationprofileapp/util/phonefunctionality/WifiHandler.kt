package com.mglock.locationprofileapp.util.phonefunctionality

import android.content.Context
import android.net.wifi.WifiManager
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class WifiHandler(private val context: Context): BaseHandler {

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    fun isWifiEnabled(): Boolean{
        return wifiManager.isWifiEnabled
    }

    fun scanAndNotifyAboutAvailableNetworks(){
        //TODO
    }

    override fun executeTask(option: DetailActionOption, optionValue: String) {
        TODO("Not yet implemented")
    }
}