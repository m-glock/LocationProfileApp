package com.mglock.locationprofileapp.services

import android.annotation.SuppressLint
import android.app.Service
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.views.MainActivity

class LocationUpdateService: Service() {

    companion object{
        var isRunning = false
    }

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val mLocationRequest = LocationRequest
        .create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(20000) //TODO 5-15min?
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            //TODO remove this when finished, only for testing
            val lastLocation = locationResult.lastLocation
            Toast.makeText(
                applicationContext,
                "Location: ${lastLocation.latitude}/${lastLocation.longitude}",
                Toast.LENGTH_SHORT
            ).show()
            Log.i("new location", "${lastLocation.latitude}/${lastLocation.longitude}")
        }
    }
    private val mChannelId = "LocationNotificationChannel"

    override fun onBind(p0: Intent?): IBinder? { return null }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        // built intent and notification for foreground service
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification.Builder(this, mChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getText(R.string.app_name))
            .setContentText("The App is currently tracking you location " +
                    "and accessing functionality such as Bluetooth and Volume.")
            .setContentIntent(pendingIntent)
            .build()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()

        startForeground(1, notification)
        isRunning = true
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            mChannelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    @SuppressLint("MissingPermission")
    private fun getLocationUpdates(){
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }

    // this method determined whether location is enabled
    // if it is not enabled, a dialog will be shown to enable the location
    /*private fun checkCurrentLocationSettings(){
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)

        val locationSettingsTask: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

        // if location services are activated
        locationSettingsTask.addOnSuccessListener { response ->
            val states = response.locationSettingsStates
            if (states != null) {
                getLastKnownLocation()
            }
        }

        // if location services are not enabled, request the user to change that
        locationSettingsTask.addOnFailureListener{ exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        this@MainActivity,
                        REQUEST_CHECK_SETTINGS)
                }  catch (exception: IntentSender.SendIntentException) {
                    // Ignore the error.
                } catch (exception: ClassCastException) {
                    // Ignore, should be an impossible error.
                }
            } else if(exception is ApiException){
                if(exception.status.statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                    // flight mode might be active, show the user a dialog
                    Log.i(
                        "SETTINGS_CHANGE_UNAVAILABLE",
                        "An error has occurred. Your flight mode might be enabled.",
                        exception
                    )
                }
            } else {
                Log.e(
                    "Unknown exception",
                    "An unknown exception has occurred when checking the current location settings.",
                    exception
                )
            }
        }
    }*/
}