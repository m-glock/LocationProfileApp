package com.mglock.locationprofileapp.util

import android.content.IntentSender
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.mglock.locationprofileapp.views.profiles.fragments.ProfilesFragment

class LocationUtil {

    companion object{
        private val mLocationRequest = LocationRequest
            .create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(20000)

        fun checkCurrentLocationSettings(fragment: ProfilesFragment, onLocationActivated : () -> Unit){
            // get current location settings
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)

            val locationSettingsTask: Task<LocationSettingsResponse> =
                LocationServices.getSettingsClient(fragment.requireContext()).checkLocationSettings(builder.build())

            // if location services are activated
            locationSettingsTask.addOnSuccessListener { response ->
                onLocationActivated()
            }

            // if location services are not enabled, request the user to change that
            locationSettingsTask.addOnFailureListener{ exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                            fragment.requireActivity(),
                            1)
                    }  catch (exception: IntentSender.SendIntentException) {
                        // Ignore the error.
                    } catch (exception: ClassCastException) {
                        // Ignore, should be an impossible error.
                    }
                } else if(exception is ApiException){
                    if(exception.status.statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                        // flight mode might be active, show the user a dialog
                        Toast.makeText( fragment.requireContext(),
                            "Settings change unavailable. Your flight mode might be enabled.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText( fragment.requireContext(),
                        "Please turn on your location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}