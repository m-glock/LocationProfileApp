package com.mglock.locationprofileapp.util.phonefunctionality

import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.util.enums.DetailActionOption


class BluetoothHandler(private val context: Context): BaseHandler {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    // will be false unless permissions are granted
    private var isBluetoothEnabled: Boolean = false
    private val notificationChannelId = "Bluetooth Notification"
    private val notificationID = 21

    init {
        Dexter.withContext(context)
            .withPermissions(
                DetailActionOption.NOTIFY_BLUETOOTH_ENABLED.getRequiredPermissions()
            ).withListener(PermissionListener(context){
                val adapter = bluetoothManager.adapter
                isBluetoothEnabled = adapter?.isEnabled ?: false
            })
            .check()
    }

    // get information
    private fun checkIfBluetoothEnabled(): Boolean{
        val adapter = bluetoothManager.adapter
        isBluetoothEnabled = adapter?.isEnabled ?: false
        return isBluetoothEnabled
    }

    // actions
    private fun sendBluetoothNotificationStatus(enabledMode: String){
        val shouldBeEnabled = enabledMode == "enabled"
        if(checkIfBluetoothEnabled() != shouldBeEnabled){
            val notification = NotificationCompat.Builder(context, notificationChannelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bluetooth is not $enabledMode")
                .setContentText("Your bluetooth is not $enabledMode as expected. " +
                        "You might want to change this.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)
            notificationManager.notify(notificationID, notification)
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = "Bluetooth Notification Channel"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(notificationChannelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
    }

    override fun executeTask(option: DetailActionOption, optionValue: String, optionMode: String?) {
        when(option){
            DetailActionOption.NOTIFY_BLUETOOTH_ENABLED ->
                sendBluetoothNotificationStatus(optionValue)
            else -> return
        }
    }

}