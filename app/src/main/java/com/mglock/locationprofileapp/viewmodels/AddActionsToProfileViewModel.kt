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
import kotlinx.coroutines.launch

class AddActionsToProfileViewModel(app: Application): AndroidViewModel(app)  {

    private var _actions: LiveData<List<DetailAction>> = MutableLiveData(mutableListOf())
    val actions get() = _actions

    fun getActions(profileId: Long){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _actions = if(profileId == -1L){
                    db.detailActionDao().getByNoProfile().asLiveData(coroutineContext)
                } else {
                    db.detailActionDao().getByProfile(profileId).asLiveData(coroutineContext)
                }
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updateAction(detailAction: DetailAction){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.detailActionDao().update(detailAction)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun deleteAction(detailAction: DetailAction){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.detailActionDao().delete(detailAction)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun removeAllActionsWithNoProfile(){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.detailActionDao().deleteAllWithNoProfile()
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}