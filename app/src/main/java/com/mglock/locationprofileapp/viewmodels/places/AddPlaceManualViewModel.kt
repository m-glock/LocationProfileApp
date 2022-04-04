package com.mglock.locationprofileapp.viewmodels.places

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import kotlinx.coroutines.launch

class AddPlaceManualViewModel(app: Application): AndroidViewModel(app) {

    private val _sliderValue: MutableLiveData<Int> = MutableLiveData<Int>()
    private var mLatitude: Double? = null
    private var mLongitude: Double? = null
    val sliderValue get(): MutableLiveData<Int> = _sliderValue
    var place: MutableLiveData<Place> = MutableLiveData()
    var buttonText: MutableLiveData<String> = MutableLiveData("Add Place")

    private fun addPlace(title: String, address: String, range: Float){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.placeDao().insert(Place(
                    0,
                    title,
                    address,
                    mLatitude!!,
                    mLongitude!!,
                    range
                ))
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    private fun updatePlace(place: Place){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.placeDao().update(place)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updateLatLng(latLng: LatLng){
        mLatitude = latLng.latitude
        mLongitude = latLng.longitude
    }

    fun addOrUpdatePlace(newPlaceTitle: String, address: String, range: Float) {
        val place = place.value
        if(place != null){
            place.title = newPlaceTitle
            place.address = address
            place.range = range
            updatePlace(place)
        } else {
            addPlace(newPlaceTitle, address, range)
        }
    }
}