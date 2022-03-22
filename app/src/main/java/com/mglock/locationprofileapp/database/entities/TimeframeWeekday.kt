package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeframe_weekday")
data class TimeframeWeekday (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeframe_weekday_uid")
    val timeframeWeekdayUID: Long,

    @ColumnInfo(name="timeframe_id")
    val timeframeId: Long,

    val weekday: String
)