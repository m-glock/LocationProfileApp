package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value= ["title", "address"], unique = true)])
data class Place (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "place_uid")
    val placeUID: Long,

    var title: String,

    var address: String?,

    val latitude: Double,

    val longitude: Double,

    var range: Float
): Serializable