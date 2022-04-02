package com.mglock.locationprofileapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import kotlinx.coroutines.runBlocking

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
        val triggeringGeofences = geofencingEvent.triggeringGeofences

        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            triggeringGeofences.forEach { geofence ->
                val receiverContext = context!!
                runBlocking {
                    executeProfileActions(geofence, receiverContext)
                }
            }
        } else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            // TODO reset or just ignore?
        } else {
            // Log the error.
            Log.e("Unknown Geofence Event", "The geofence event that was triggered is unknown.")
        }
    }

    private suspend fun executeProfileActions(geofence: Geofence, context: Context){
        val db = AppDatabase.getInstance(context)
        val profiles = db.profileDao().getByPlaceWithRelations(geofence.requestId.toLong())
        profiles.forEach { profile ->
            profile.actions.forEach { action ->
                DetailActionOption.delegateTaskToHandler(
                    action.title,
                    action.detailActionValue,
                    action.mode,
                    context
                )
            }
        }
    }
}