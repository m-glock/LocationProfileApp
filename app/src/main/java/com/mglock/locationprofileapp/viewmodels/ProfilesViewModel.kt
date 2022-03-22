package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Profile
import kotlinx.coroutines.launch

class ProfilesViewModel(app: Application): AndroidViewModel(app) {

    private var _profiles: MutableLiveData<MutableList<Profile>> = MutableLiveData()
    val profiles get() = _profiles

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                //_profiles.value = db.profileDao().getAll().toMutableList()
                _profiles.value = mutableListOf(
                    Profile(0, "Homeoffice", 1, 1, true),
                    Profile(0, "Office", 1, 1, false),
                    Profile(0, "Night", 1, 1, true)
                )
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}