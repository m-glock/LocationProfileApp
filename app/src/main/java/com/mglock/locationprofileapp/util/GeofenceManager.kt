package com.mglock.locationprofileapp.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations

class GeofenceManager {

    @SuppressLint("MissingPermission")
    fun startGeofencing(
        activeProfiles: List<ProfileWithRelations>,
        geofencingClient: GeofencingClient,
        geofencePendingIntent: PendingIntent
    ){
        val geofenceList = activeProfiles.map { profile ->
            Geofence.Builder()
                .setRequestId(profile.place!!.placeUID.toString())
                .setCircularRegion(
                    profile.place!!.latitude,
                    profile.place!!.longitude,
                    profile.place!!.range
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(profile.profile.placeTransition!!.id)
                .build()
        }.toMutableList()

        geofencingClient.addGeofences(
            getGeofencingRequest(geofenceList),
            geofencePendingIntent
        ).run{}
    }

    fun stopGeofencing(geofencingClient: GeofencingClient, geofencePendingIntent: PendingIntent){
        geofencingClient.removeGeofences(geofencePendingIntent).run{}
    }

    // add the known geofence location to monitor them
    private fun getGeofencingRequest(geofenceList: MutableList<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }
}