package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.ActionGroup
import kotlinx.coroutines.launch

class ActionsViewModel(app: Application): AndroidViewModel(app) {

    private var _actionGroups: MutableLiveData<List<ActionGroup>> = MutableLiveData()
    val actionGroups get() = _actionGroups

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _actionGroups.value = db.actionGroupDao().getAll()

                //TODO see if there is a better solution
                if(_actionGroups.value?.isEmpty() == true){
                    _actionGroups.value = db.actionGroupDao().getAll()
                }
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