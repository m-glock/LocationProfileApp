package com.mglock.locationprofileapp.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value= ["title", "address"], unique = true)])
data class Place (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val title: String,
    val address: String?,
    val latitude: String,
    val longitude: String,
    val range: Int
)