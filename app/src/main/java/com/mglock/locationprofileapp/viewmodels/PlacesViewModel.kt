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
                //_places.value = db.placeDao().getAll().toMutableList()
                _places.value = mutableListOf(
                    Place(0, "Zuhause", "Trojanstr. 2, 12437 Berlin", "324567", "76543", 5),
                    Place(0, "Uni", "Wilhelminenhofstraße 75A, 12459 Berlin", "324567", "76543", 5),
                    Place(0, "Arbeit", "Köpenicker Str. 9, 10997 Berlin", "324567", "76543", 5),
                )
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
                //TODO update place in DB
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

}