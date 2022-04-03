package com.mglock.locationprofileapp.util.enums

import com.google.android.gms.location.Geofence

enum class PlaceTransition(val title: String, val id: Int) {
    TRANSITION_ENTER("Enter", Geofence.GEOFENCE_TRANSITION_ENTER),
    TRANSITION_EXIT("Exit", Geofence.GEOFENCE_TRANSITION_EXIT),
    TRANSITION_DWELL("Dwell", Geofence.GEOFENCE_TRANSITION_DWELL)
}