package com.mglock.locationprofileapp.util.phonefunctionality

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class AudioHandler(private val context: Context): BaseHandler {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    enum class VolumeStreamTypes(val title: String, val id: Int){
        MEDIA_VOLUME("Media Volume", AudioManager.STREAM_MUSIC),
        SYSTEM_VOLUME("System Volume", AudioManager.STREAM_SYSTEM),
        ALARM_VOLUME("Alarm Volume", AudioManager.STREAM_ALARM)
    }

    enum class VolumeModes(val title: String, val id: Int){
        MODE_NORMAL("normal", AudioManager.RINGER_MODE_NORMAL),
        MODE_SILENT("do not disturb", AudioManager.RINGER_MODE_SILENT),
        MODE_VIBRATE("vibrate", AudioManager.RINGER_MODE_VIBRATE)
    }

    enum class RingtoneTypes(val title: String, val id: Int){
        ALL("All", RingtoneManager.TYPE_ALL),
        ALARM("Alarm", RingtoneManager.TYPE_ALARM),
        NOTIFICATION("Notification", RingtoneManager.TYPE_NOTIFICATION),
        RINGTONE("Ringtone", RingtoneManager.TYPE_RINGTONE)
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
    private fun changeVolume(volumeValue: String, streamTypeName: String){
        val streamType = VolumeStreamTypes.values()
            .find { type -> type.title == streamTypeName}
        val volume = volumeValue.toInt()
        if(streamType != null)
            audioManager.setStreamVolume(streamType.id, volume, AudioManager.FLAG_SHOW_UI)
    }

    private fun changeVolumeMode(ringerModeName: String){
        val ringerMode = VolumeModes.values().find { mode -> mode.title == ringerModeName}
        if(ringerMode != null)
            audioManager.ringerMode = ringerMode.id
    }

    private fun changeRingtone(value: String, ringtoneTypeName: String){
        val ringtoneType: Int = RingtoneTypes.values()
            .find { type -> type.title == ringtoneTypeName }!!.id
        val newRingtone: Uri = Uri.parse(value)
        RingtoneManager.setActualDefaultRingtoneUri(context, ringtoneType, newRingtone)
    }

    override fun executeTask(option: DetailActionOption, optionValue: String, optionMode: String?) {
        when(option){
            DetailActionOption.CHANGE_VOLUME -> changeVolume(optionValue,optionMode!!)
            DetailActionOption.CHANGE_VOLUME_MODE -> changeVolumeMode(optionValue)
            DetailActionOption.CHANGE_RINGTONE -> changeRingtone(optionValue,optionMode!!)
            else -> return
        }
    }
}