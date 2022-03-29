package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.Timeframe

@Dao
interface TimeframeDao {
    @Query("SELECT * FROM timeframe")
    suspend fun getAll(): List<Timeframe>

    @Query("SELECT * FROM timeframe WHERE timeframe_uid IN (:timeframeIds)")
    suspend fun getByIds(timeframeIds: LongArray): List<Timeframe>

    @Insert
    suspend fun insert(timeframe: Timeframe): Long

    @Insert
    fun insertAll(vararg timeframes: Timeframe)

    @Delete
    suspend fun delete(timeframe: Timeframe)
}