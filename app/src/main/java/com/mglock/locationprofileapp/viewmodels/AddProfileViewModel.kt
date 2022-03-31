package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.Timeframe
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.util.Time
import com.mglock.locationprofileapp.util.enums.Weekday
import kotlinx.coroutines.launch

class AddProfileViewModel(app: Application): AndroidViewModel(app)  {

    private var _places: LiveData<List<Place>> = MutableLiveData()
    val places get() = _places

    private var _actions: LiveData<List<DetailAction>> = MutableLiveData(mutableListOf())
    val actions get() = _actions

    private var _timeStart: MutableLiveData<Time> = MutableLiveData()
    val timeStart get() = _timeStart

    private var _timeEnd: MutableLiveData<Time> = MutableLiveData()
    val timeEnd get() = _timeEnd

    var profile: MutableLiveData<ProfileWithRelations> = MutableLiveData()
    var buttonText: MutableLiveData<String> = MutableLiveData("Add Profile")

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

    fun setStartTime(timeStart: Time){
        _timeStart.value = timeStart
    }

    fun setEndTime(timeEnd: Time){
        _timeEnd.value = timeEnd
    }

    fun setActions(){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _actions = if(profile.value == null){
                    db.detailActionDao().getByNoProfile().asLiveData(coroutineContext)
                } else {
                    db.detailActionDao().getByProfile(profile.value!!.profile.profileUID).asLiveData(coroutineContext)
                }
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun allInputsSet(
        usePlace: Boolean,
        useTimeframe: Boolean,
        title: String?
    ): Boolean{
        val atLeastOneChecked = usePlace || useTimeframe
        val isTitleSet = title?.isNotBlank()
        val isTimeSet = if(useTimeframe) {
            timeStart.value != null && timeEnd.value != null
        } else useTimeframe
        val atLeastOneAction = actions.value!!.isNotEmpty()
        return atLeastOneChecked && atLeastOneAction && (isTitleSet ?: false || isTimeSet)
    }

    fun addOrUpdateProject(
        projectTitle: String,
        usePlace: Boolean,
        useTimeframe: Boolean,
        selectedPlaceTitle: String,
        weekdays: Set<Weekday>
    ){
        if(profile.value != null){
            updateProfile(projectTitle, selectedPlaceTitle, weekdays, usePlace, useTimeframe)
        } else {
            addProfile(projectTitle, selectedPlaceTitle, weekdays, usePlace, useTimeframe)
        }
    }

    private fun updateProfile(
        title: String,
        selectedPlaceTitle: String,
        weekdays: Set<Weekday>,
        usePlace: Boolean,
        useTimeframe: Boolean
    ) {
        viewModelScope.launch {
            try {
                // update profile and its relations
                val db = AppDatabase.getInstance(getApplication())
                val profileWithRelations = profile.value!!

                // if place had been checked, update the place id of the profile
                if(usePlace){
                    profileWithRelations.profile.placeId = places.value!!.find { place ->
                        place.title == selectedPlaceTitle
                    }?.placeUID
                } else {
                    profileWithRelations.profile.placeId = null
                }

                if(useTimeframe){
                    if(profileWithRelations.profile.timeframeId == null){
                        // new timeframe, add it to db
                        val timeframeUID = db.timeframeDao().insert(Timeframe(
                            0,
                            timeStart.value!!,
                            timeEnd.value!!,
                            false,
                            weekdays
                        ))
                        profileWithRelations.profile.timeframeId = timeframeUID
                    } else {
                        // old timeframe, update it
                        profileWithRelations.timeframe!!.from = timeStart.value!!
                        profileWithRelations.timeframe.to = timeEnd.value!!
                        profileWithRelations.timeframe.weekdays = weekdays
                        db.timeframeDao().update(profileWithRelations.timeframe)
                    }
                } else {
                    profileWithRelations.profile.timeframeId = null
                }
                profileWithRelations.profile.title = title

                db.profileDao().update(profileWithRelations.profile)
            } catch (e: Exception) {
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    private fun addProfile(
        title: String,
        placeTitle: String?,
        weekdays: Set<Weekday>,
        usePlace: Boolean,
        useTimeframe: Boolean
    ){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())

                // add timeframe to database if chosen and get the ID
                var timeframeUID: Long? = null
                if(useTimeframe){
                    timeframeUID = db.timeframeDao().insert(Timeframe(
                        0,
                        timeStart.value!!,
                        timeEnd.value!!,
                        false,
                        weekdays
                    ))
                }

                // get ID of place if chosen
                var placeUID: Long? = null
                if(usePlace && !placeTitle.isNullOrBlank()){
                    val place = places.value?.find { place -> place.title == placeTitle }
                    placeUID = place?.placeUID
                }

                // add profile to DB
                val profileId = db.profileDao().insert(Profile(
                    0,
                    title,
                    placeUID,
                    timeframeUID,
                    false
                ))

                // update profile id for all previously created detail actions
                db.detailActionDao().addProfileId(profileId)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}