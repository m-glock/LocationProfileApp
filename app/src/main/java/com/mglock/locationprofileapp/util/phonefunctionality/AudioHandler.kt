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

    // get information
    fun getStreamMaxVolume(streamType: Int): Int{
        return audioManager.getStreamMaxVolume(streamType)
    }

    fun chooseNewRingtone(resultLauncher: ActivityResultLauncher<Intent>) {
        val currentTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_ALARM
        )
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone")
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)

        resultLauncher.launch(intent)

        /*
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if(data != null){
                    val uri: Uri? = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                    if (uri != null) {
                        val ringTonePath = uri.toString()
                    }
                }
            }
        }
         */
    }

    fun checkIfToDoNotDisturbIsAllowed(resultLauncher: ActivityResultLauncher<Intent>){
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (!notificationManager!!.isNotificationPolicyAccessGranted) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            resultLauncher.launch(intent)
        }
    }

    // actions
    fun muteMicrophone(){
        audioManager.isMicrophoneMute = false
    }

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