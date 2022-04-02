package com.mglock.locationprofileapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e("Geofence Event Error", errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT
        ) {
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            triggeringGeofences.forEach { geofence ->
                Log.i("Geofence detected", "Geofence ${geofence.requestId} was triggered. " +
                        "You ${if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) "entered" else "exited"} the location")
            }
        } else {
            // Log the error.
            Log.e("Unknown Geofence Event", "The geofence event that was triggered is unknown.")
        }
    }
}