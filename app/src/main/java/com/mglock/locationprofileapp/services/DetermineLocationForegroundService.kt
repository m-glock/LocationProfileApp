package com.mglock.locationprofileapp.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mglock.locationprofileapp.views.MainActivity
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import java.util.TimerTask
import java.util.Timer

class DetermineLocationForegroundService: Service() {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val mChannelId = "LocationNotificationChannel"
    private val locationList: MutableList<String> = mutableListOf()

    private val mLocationRequest = LocationRequest
        .create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(20000) //TODO find right interval so that location is not requested too often

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val lastLocation = locationResult.lastLocation
            Toast.makeText(
                applicationContext,
                "Location: ${lastLocation.latitude}/${lastLocation.longitude}",
                Toast.LENGTH_SHORT
            ).show()
            locationList.add("${lastLocation.latitude}/${lastLocation.longitude}")
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    //TODO stop other foreground service so that they don't interfere?
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // create notification that is shown while the service is running
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(this, mChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //TODO replace
            .setContentTitle(getText(R.string.app_name))
            .setContentText("The App is currently tracking you location to create a new place.")
            .setContentIntent(pendingIntent)
            .build()

        val timeInMs = intent!!.extras!!.get("timeInMs") as Long
        val newPlaceTitle = intent.extras!!.get("newPlaceTitle") as String

        // specify time after which the service should stop
        val myTask: TimerTask = object : TimerTask() {
            override fun run() {
                val db = AppDatabase.getInstance(this@DetermineLocationForegroundService)
                // TODO create place object from information
                val place = Place(0, newPlaceTitle, "unknown", 0.0, 0.0, 0F)
                db.placeDao().insertAll(place)
                stopSelf()
            }
        }
        val myTimer = Timer()
        myTimer.schedule(myTask, timeInMs)

        // start location services and request updates
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()

        startForeground(1, notification)
        return START_NOT_STICKY
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
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }
}