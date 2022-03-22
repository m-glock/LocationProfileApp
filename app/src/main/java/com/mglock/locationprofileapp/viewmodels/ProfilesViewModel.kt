package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import kotlinx.coroutines.launch

class ProfilesViewModel(app: Application): AndroidViewModel(app) {

    private var _profiles: MutableLiveData<MutableList<ProfileWithRelations>> = MutableLiveData()
    val profiles get() = _profiles

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                //_profiles.value = db.profileDao().getAllWithRelations().toMutableList()
                /*_profiles.value = mutableListOf(
                    ProfileWithRelations(
                        Profile(0, "Homeoffice", 1, 1, true),
                        Place(0, "Home", "Address", "1", "1", 3)
                    ),
                    ProfileWithRelations(
                        Profile(0, "Work at office", 1, 1, false),
                        null
                    ),
                    ProfileWithRelations(
                        Profile(0, "Night", 1, 1, true),
                        Place(0, "Home", "Address", "1", "1", 3)
                    ),
                )*/
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}