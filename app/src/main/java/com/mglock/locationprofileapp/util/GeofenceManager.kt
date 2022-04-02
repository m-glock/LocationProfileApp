package com.mglock.locationprofileapp.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.mglock.locationprofileapp.database.entities.Place

class GeofenceManager {

    @SuppressLint("MissingPermission")
    fun startGeofencing(places: List<Place>, geofencingClient: GeofencingClient, geofencePendingIntent: PendingIntent){
        val geofenceList = places.map { place ->
            Geofence.Builder()
                .setRequestId(place.placeUID.toString())
                .setCircularRegion(
                    place.latitude,
                    place.longitude,
                    place.range
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
        }.toMutableList()

        geofencingClient.addGeofences(getGeofencingRequest(geofenceList), geofencePendingIntent).run{
            addOnSuccessListener {
                // TODO
            }
            addOnFailureListener {
                // TODO
            }
        }
    }

    fun stopGeofencing(geofencingClient: GeofencingClient, geofencePendingIntent: PendingIntent){
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                // TODO
            }
            addOnFailureListener {
                // TODO
            }
        }

    }

    // add the known geofence location to monitor them
    private fun getGeofencingRequest(geofenceList: MutableList<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }
}