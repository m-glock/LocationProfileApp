package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mglock.locationprofileapp.Weekday
import java.sql.Date

@Entity
data class Timeframe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeframe_uid")
    val timeframeUID: Long,

    val from: Date,

    val to: Date,

    @ColumnInfo(name="am_pm")
    val amPm: Boolean,

    val weekdays: List<Weekday>
)