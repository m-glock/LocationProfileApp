package com.mglock.locationprofileapp.util.phonefunctionality

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class NotificationHandler(private val context: Context): BaseHandler {

    private val notificationChannelId = "General Notification"
    private val notificationID = 65

    // actions
    private fun sendNotification(message: String){
        val notification = NotificationCompat.Builder(context, notificationChannelId)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_action_check)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        notificationManager.notify(notificationID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = "Notification Channel"
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
            DetailActionOption.NOTIFY_LOCATION_ENTERED_EXITED ->
                sendNotification(optionValue)
            else -> return
        }
    }
}