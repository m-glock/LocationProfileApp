package com.mglock.databasetest.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.databasetest.entities.TimeframeWeekday

@Dao
interface TimeframeWeekdayDao {
    @Query("SELECT * FROM timeframe_weekday")
    fun getAll(): List<TimeframeWeekday>

    @Query("SELECT * FROM timeframe_weekday WHERE uid IN (:placeIds)")
    fun getByIds(placeIds: IntArray): List<TimeframeWeekday>

    @Query("SELECT * FROM timeframe_weekday WHERE timeframe_id LIKE (:timeframeId)")
    fun getByTimeframeId(timeframeId: Long): List<TimeframeWeekday>

    @Insert
    fun insertAll(vararg timeframeWeekdays: TimeframeWeekday)

    @Delete
    fun delete(timeframeWeekday: TimeframeWeekday)
}