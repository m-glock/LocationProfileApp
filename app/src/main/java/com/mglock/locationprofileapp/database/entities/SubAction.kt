package com.mglock.databasetest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_action")
data class SubAction(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val name: String,
    @ColumnInfo(name="action_group_id") val actionGroupId: Long
)