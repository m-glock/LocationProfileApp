package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value= ["title"], unique = true)])
data class Profile(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_uid")
    val profileUID: Long,

    val title: String,

    @ColumnInfo(name="place_id")
    val placeId: Long?,

    @ColumnInfo(name="timeframe_id")
    val timeframeId: Long?,

    var active: Boolean
)