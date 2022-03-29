package com.mglock.locationprofileapp.util

import java.io.Serializable

data class Time(
    val hours: Int,
    val minutes: Int
): Serializable {
    override fun toString(): String {
        return "${hours}:${minutes}"
    }
}