package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "action_group", indices = [Index(value= ["title"], unique = true)])
data class ActionGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "action_group_uid")
    val actionGroupUID: Long,

    val title: String,

    var active: Boolean
)