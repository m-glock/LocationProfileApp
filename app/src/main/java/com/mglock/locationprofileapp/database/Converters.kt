package com.mglock.locationprofileapp.database

import androidx.room.TypeConverter
import com.mglock.locationprofileapp.Weekday
import java.sql.Date
import java.util.Locale

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun weekdayToString(weekdays: List<Weekday>): String{
        return weekdays.joinToString{ weekday -> weekday.title }
    }

    @TypeConverter
    fun stringToWeekdays(weekdaysString: String): List<Weekday> {
        if (weekdaysString.isEmpty()) return emptyList()
        val weekdays = weekdaysString.split(",").map { weekday ->
            val wU = weekday.uppercase(Locale.getDefault()).trim()
            Weekday.valueOf(wU)
        }
        return weekdays
    }

}