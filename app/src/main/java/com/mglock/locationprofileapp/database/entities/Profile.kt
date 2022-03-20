package com.mglock.databasetest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(indices = [Index(value= ["name", "icon"], unique = true)])
data class Profile(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val name: String,
    @ColumnInfo(name="place_id") val placeId: Long,
    @ColumnInfo(name="timeframe_id") val timeframeId: Long,
    val active: Boolean,
    val icon: String, //TODO
)