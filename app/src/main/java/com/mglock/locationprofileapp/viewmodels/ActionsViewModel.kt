package com.mglock.locationprofileapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.ActionGroup

class ActionsViewModel(app: Application): AndroidViewModel(app) {

    private var _actionGroups: MutableLiveData<List<ActionGroup>> = MutableLiveData()
    val actionGroups get() = _actionGroups

    init {
        val db = AppDatabase.getInstance(getApplication())
        _actionGroups.value = db.actionGroupDao().getAll()
    }
}