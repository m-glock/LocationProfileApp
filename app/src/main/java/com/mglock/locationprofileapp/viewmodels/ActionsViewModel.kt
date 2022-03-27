package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.ActionGroup
import kotlinx.coroutines.launch

class ActionsViewModel(app: Application): AndroidViewModel(app) {

    private var _actionGroups: LiveData<List<ActionGroup>> = MutableLiveData()
    val actionGroups get() = _actionGroups

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _actionGroups = db.actionGroupDao().getAll().asLiveData(this.coroutineContext)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updateActionGroup(actionGroup: ActionGroup){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.actionGroupDao().update(actionGroup)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}