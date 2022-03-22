package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_detail_action")
data class ProfileDetailAction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_detail_action_uid")
    val profileDetailActionUID: Long,

    @ColumnInfo(name="detail_action_uid")
    val detailActionId: Long,

    @ColumnInfo(name="detail_action_value")
    val detailActionValue: String,

    @ColumnInfo(name="profile_uid")
    val profileId: Long
)