package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_action")
data class DetailAction(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val name: String,
    @ColumnInfo(name="action_group_id") val actionGroupId: Long
)