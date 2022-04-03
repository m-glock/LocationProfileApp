package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getAll(): Flow<List<Place>>

    @Update
    suspend fun update(place: Place)

    @Insert
    suspend fun insert(place: Place)

    @Insert
    fun insertAll(vararg places: Place)

    @Delete
    suspend fun delete(place: Place)
}