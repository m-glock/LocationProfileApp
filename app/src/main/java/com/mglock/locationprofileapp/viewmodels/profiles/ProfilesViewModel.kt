package com.mglock.locationprofileapp.viewmodels.profiles

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import kotlinx.coroutines.launch

class ProfilesViewModel(app: Application): AndroidViewModel(app) {

    private var _profiles: LiveData<List<ProfileWithRelations>> = MutableLiveData()
    val profiles get() = _profiles

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _profiles = db.profileDao().getAllWithRelations().asLiveData(this.coroutineContext)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun deleteProfile(profile: Profile){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.profileDao().delete(profile)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updateProfile(profile: Profile){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.profileDao().update(profile)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
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