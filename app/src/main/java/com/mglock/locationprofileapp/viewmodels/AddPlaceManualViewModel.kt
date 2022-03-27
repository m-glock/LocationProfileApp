package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import kotlinx.coroutines.launch

class AddPlaceManualViewModel(app: Application): AndroidViewModel(app) {

    fun addPlace(title: String, address: String, latitude: Double, longitude: Double){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.placeDao().insert(Place(
                    0,
                    title,
                    address,
                    latitude.toString(),
                    longitude.toString()
                ))
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updatePlace(place: Place){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.placeDao().update(place)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}