package com.mglock.locationprofileapp.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "action_group", indices = [Index(value= ["title"], unique = true)])
data class ActionGroup(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val title: String,
    val active: Boolean
)