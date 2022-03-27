package com.mglock.locationprofileapp.database

import androidx.room.TypeConverter
import com.mglock.locationprofileapp.util.enums.Weekday
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
    fun weekdayToString(weekdays: Set<Weekday>): String{
        return weekdays.joinToString{ weekday -> weekday.title }
    }

    @TypeConverter
    fun stringToWeekdays(weekdaysString: String): Set<Weekday> {
        if (weekdaysString.isEmpty()) return emptySet()
        val weekdays = weekdaysString.split(",").map { weekday ->
            val wU = weekday.uppercase(Locale.getDefault()).trim()
            Weekday.valueOf(wU)
        }
        return weekdays.toSet()
    }

}