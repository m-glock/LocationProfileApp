package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeframe_weekday")
data class TimeframeWeekday (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name="timeframe_id") val timeframeId: Long,
    val weekday: String
)