package com.mglock.locationprofileapp.viewmodels.places

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import kotlinx.coroutines.launch

class PlacesViewModel(app: Application): AndroidViewModel(app) {

    private var _places: LiveData<List<Place>> = MutableLiveData()
    val places get() = _places

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _places = db.placeDao().getAll().asLiveData(this.coroutineContext)
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

}