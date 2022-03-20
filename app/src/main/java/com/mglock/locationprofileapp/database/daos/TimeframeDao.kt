package com.mglock.databasetest.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.databasetest.entities.Timeframe

@Dao
interface TimeframeDao {
    @Query("SELECT * FROM timeframe")
    fun getAll(): List<Timeframe>

    @Query("SELECT * FROM timeframe WHERE uid IN (:timeframeIds)")
    fun getByIds(timeframeIds: IntArray): List<Timeframe>

    @Insert
    fun insertAll(vararg timeframes: Timeframe)

    @Delete
    fun delete(timeframe: Timeframe)
}