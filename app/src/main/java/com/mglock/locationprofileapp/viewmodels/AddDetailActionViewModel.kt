package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.DetailAction
import kotlinx.coroutines.launch

class AddDetailActionViewModel(app: Application): AndroidViewModel(app) {

    fun addAction(detailAction: DetailAction){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                // add detail action to DB
                db.detailActionDao().insert(detailAction)
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}