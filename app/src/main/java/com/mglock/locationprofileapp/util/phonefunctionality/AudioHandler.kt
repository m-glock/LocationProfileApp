package com.mglock.locationprofileapp.util.phonefunctionality

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher

class AudioHandler(private val context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    enum class VolumeStreamTypes(val title: String, val value: Int){
        MEDIA_VOLUME("Media Volume", AudioManager.STREAM_MUSIC),
        SYSTEM_VOLUME("System Volume", AudioManager.STREAM_SYSTEM),
        ALARM_VOLUME("Alarm Volume", AudioManager.STREAM_ALARM)
    }

    enum class VolumeModes(val title: String){
        MODE_NORMAL("normal"),
        MODE_SILENT("do not disturb"),
        MODE_VIBRATE("vibrate")
    }

    // get information
    fun getStreamMaxVolume(streamType: Int): Float{
        return audioManager.getStreamMaxVolume(streamType).toFloat()
    }

    fun chooseNewRingtone(resultLauncher: ActivityResultLauncher<Intent>) {
        val currentTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_ALARM
        )
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select new Ringtone")
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)

        resultLauncher.launch(intent)
    }

    fun isDoNotDisturbAllowed(): Boolean{
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        return notificationManager!!.isNotificationPolicyAccessGranted
    }

    // actions
    fun changeVolume(streamType: Int, value: Int){
        audioManager.setStreamVolume(streamType, value, AudioManager.FLAG_SHOW_UI)
    }

    fun changeVolumeMode(ringerMode: Int){
        audioManager.ringerMode = ringerMode
    }

    fun changeRingtone(newRingtone: Uri, ringtoneType: Int){
        RingtoneManager.setActualDefaultRingtoneUri(context, ringtoneType, newRingtone)
    }
}