package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.DetailAction
import kotlinx.coroutines.launch

class ActionsViewModel(app: Application): AndroidViewModel(app) {

    private var _actions: LiveData<List<DetailAction>> = MutableLiveData()
    val actions get() = _actions

    init {
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                _actions = db.detailActionDao().getAll().asLiveData(this.coroutineContext)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }

    fun updateDetailAction(detailAction: DetailAction){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                db.detailActionDao().update(detailAction)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}