package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile_detail_action",
    primaryKeys = ["profile_uid","detail_action_uid"]
)
data class ProfileDetailAction(
    @ColumnInfo(name="profile_uid", index = true)
    val profileId: Long,

    @ColumnInfo(name="detail_action_uid")
    val detailActionId: Long
)