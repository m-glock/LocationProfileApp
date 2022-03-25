package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import kotlinx.coroutines.launch

class PlacesViewModel(app: Application): AndroidViewModel(app) {

    private var _places: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val places get() = _places

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _places.value = db.placeDao().getAll().toMutableList()
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun deletePlace(place: Place){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.placeDao().delete(place)
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