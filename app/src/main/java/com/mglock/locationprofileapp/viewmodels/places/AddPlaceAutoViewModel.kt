package com.mglock.locationprofileapp.viewmodels.places

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.mglock.locationprofileapp.services.DetermineLocationForegroundService

class AddPlaceAutoViewModel(private val app: Application): AndroidViewModel(app) {

    fun startLocationTracking(newPlaceTitle: String, locationServiceDuration: String){
        val timeInMs: Long = when(locationServiceDuration){
            "2 hours" -> 7200000
            "4 hours" -> 14400000
            "8 hours" -> 28800000
            else -> 3600000 // 1 hour
        }
        val serviceIntent = Intent(app.applicationContext, DetermineLocationForegroundService::class.java)
        serviceIntent.putExtra("timeInMs", timeInMs)
        serviceIntent.putExtra("newPlaceTitle", newPlaceTitle)
        ContextCompat.startForegroundService(app.applicationContext, serviceIntent)
    }
}