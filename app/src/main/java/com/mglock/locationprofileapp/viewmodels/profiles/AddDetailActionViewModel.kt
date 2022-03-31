package com.mglock.locationprofileapp.viewmodels.profiles

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.DetailAction
import kotlinx.coroutines.launch

class AddDetailActionViewModel(app: Application): AndroidViewModel(app) {

    fun addAction(detailAction: DetailAction, context: Context){
        viewModelScope.launch {
            try{
                val db = AppDatabase.getInstance(getApplication())
                // add detail action to DB

                // check if there is already an action with the same title and project in the db
                val allActions = db.detailActionDao().getByTitle(detailAction.title.toString())
                val actionAlreadySetForProject =
                    allActions.any { action ->  action.profileUID == detailAction.profileUID}

                if(!actionAlreadySetForProject){
                    db.detailActionDao().insert(detailAction)
                } else {
                    AlertDialog.Builder(context)
                        .setTitle("Cannot add action")
                        .setMessage("An action of the same type already exists for this project. " +
                                "You can not use the same action with multiple values for a profile")
                        .setPositiveButton("Understood", null)
                        .show()
                }
            } catch(e: Exception){
                Log.e("Error", e.stackTraceToString())
            }
        }
    }
}