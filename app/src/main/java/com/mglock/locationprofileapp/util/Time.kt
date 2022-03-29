package com.mglock.locationprofileapp.util

data class Time(
    val hours: Int,
    val minutes: Int
){
    override fun toString(): String {
        return "${hours}:${minutes}"
    }
}