package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    suspend fun getAll(): List<Place>

    @Query("SELECT * FROM place WHERE uid IN (:placeIds)")
    suspend fun getByIds(placeIds: IntArray): List<Place>

    @Query("SELECT * FROM place WHERE title LIKE (:title)")
    suspend fun getByTitle(title: String): List<Place>

    @Insert
    suspend fun insertAll(vararg places: Place)

    @Delete
    suspend fun delete(place: Place)
}