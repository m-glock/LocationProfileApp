package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    suspend fun getAll(): List<Place>

    @Query("SELECT * FROM place WHERE place_uid IN (:placeIds)")
    suspend fun getByIds(placeIds: LongArray): List<Place>

    @Query("SELECT * FROM place WHERE title LIKE (:title)")
    suspend fun getByTitle(title: String): List<Place>

    @Update
    suspend fun update(place: Place)

    @Insert
    fun insertAll(vararg places: Place)

    @Delete
    suspend fun delete(place: Place)
}