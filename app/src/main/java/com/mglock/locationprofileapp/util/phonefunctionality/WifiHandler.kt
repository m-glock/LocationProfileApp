package com.mglock.locationprofileapp.util.phonefunctionality

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.wifi.WifiManager
import androidx.core.app.NotificationCompat
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class WifiHandler(private val context: Context): BaseHandler {

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var isWifiEnabled = false
    private val notificationChannelId = "Wifi Notification"
    private val notificationID = 31

    // information
    init {
        isWifiEnabled = wifiManager.isWifiEnabled
    }

    private fun checkIfWifiEnabled(): Boolean{
        isWifiEnabled = wifiManager.isWifiEnabled
        return isWifiEnabled
    }

    // actions
    private fun sendWifiNotificationStatus(enabledMode: String){
        val shouldBeEnabled = enabledMode == "enabled"
        if(checkIfWifiEnabled() != shouldBeEnabled){
            val notification = NotificationCompat.Builder(context, notificationChannelId)
                .setContentTitle("Wifi is not $enabledMode")
                .setContentText("Your Wifi is not $enabledMode as expected. " +
                        "You might want to change this.")
                .setSmallIcon(R.drawable.ic_action_check)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)
            notificationManager.notify(notificationID, notification)
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = "Wifi Notification Channel"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(notificationChannelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
    }

    /*fun scanAndNotifyAboutAvailableNetworks(){

    }*/

    override fun executeTask(option: DetailActionOption, optionValue: String, optionMode: String?) {
        when(option){
            DetailActionOption.NOTIFY_WIFI_ENABLED ->
                sendWifiNotificationStatus(optionValue)
            else -> return
        }
    }
}