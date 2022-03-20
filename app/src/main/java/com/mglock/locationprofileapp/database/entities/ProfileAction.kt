package com.mglock.databasetest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_action")
data class ProfileAction(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name="sub_action_id") val subActionId: Long,
    @ColumnInfo(name="profile_id") val profileId: Long
)