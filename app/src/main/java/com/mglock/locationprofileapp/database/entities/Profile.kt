package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mglock.locationprofileapp.util.enums.PlaceTransition
import java.io.Serializable

@Entity(indices = [Index(value= ["title"], unique = true)])
data class Profile(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_uid")
    val profileUID: Long,

    var title: String,

    @ColumnInfo(name="place_id")
    var placeId: Long?,

    @ColumnInfo(name="place_transition")
    var placeTransition: PlaceTransition?,

    @ColumnInfo(name="timeframe_id")
    var timeframeId: Long?,

    var active: Boolean
): Serializable