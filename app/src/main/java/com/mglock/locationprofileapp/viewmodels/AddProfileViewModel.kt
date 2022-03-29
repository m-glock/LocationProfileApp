package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import kotlinx.coroutines.launch

class AddProfileViewModel(app: Application): AndroidViewModel(app)  {

    private var _places: LiveData<List<Place>> = MutableLiveData()
    val places get() = _places

    private var _timeStart: MutableLiveData<String> = MutableLiveData()
    val timeStart get() = _timeStart

    private var _timeEnd: MutableLiveData<String> = MutableLiveData()
    val timeEnd get() = _timeEnd

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

    fun setStartTime(timeStart: String){
        _timeStart.value = timeStart
    }

    fun setEndTime(timeEnd: String){
        _timeEnd.value = timeEnd
    }

    fun addProfile(profile: Profile){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.profileDao().insert(profile)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}