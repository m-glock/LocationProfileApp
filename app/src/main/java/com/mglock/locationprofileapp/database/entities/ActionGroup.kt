package com.mglock.databasetest.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "action_group", indices = [Index(value= ["name"], unique = true)])
data class ActionGroup(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val name: String,
    val active: Boolean
)