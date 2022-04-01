package com.mglock.locationprofileapp.database

import androidx.room.TypeConverter
import com.mglock.locationprofileapp.util.Time
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import com.mglock.locationprofileapp.util.enums.Weekday
import java.util.Locale

class Converters {
    @TypeConverter
    fun timestampToTime(value: String?): Time? {
        return value?.let { timeString ->
            val hAndMin = timeString.split(":")
            Time(hAndMin[0].toInt(), hAndMin[1].toInt())
        }
    }

    @TypeConverter
    fun timeToTimestamp(time: Time?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun weekdayToString(weekdays: Set<Weekday>): String{
        return weekdays.joinToString(","){ weekday -> weekday.title }
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

    @TypeConverter
    fun detailActionOptionToString(detailActionOption: DetailActionOption): String{
        return detailActionOption.title
    }

    @TypeConverter
    fun titleToDetailAction(title: String): DetailActionOption{
        return DetailActionOption.values().find { option -> option.title == title }!!
    }

}