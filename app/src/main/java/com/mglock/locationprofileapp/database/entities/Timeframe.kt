package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mglock.locationprofileapp.util.Time
import com.mglock.locationprofileapp.util.enums.Weekday
import java.io.Serializable

@Entity
data class Timeframe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeframe_uid")
    val timeframeUID: Long,

    val from: Time,

    val to: Time,

    @ColumnInfo(name="am_pm")
    val amPm: Boolean,

    val weekdays: Set<Weekday>
): Serializable {
    override fun toString(): String {
        var timeframeString = "from $from to $to"
        if(weekdays.isNotEmpty()){
            val weekdayString = weekdays.joinToString(", "){ weekday -> weekday.title }
            timeframeString = timeframeString.plus(" every $weekdayString")
        }
        return timeframeString
    }
}