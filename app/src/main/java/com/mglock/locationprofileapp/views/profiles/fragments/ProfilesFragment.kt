package com.mglock.locationprofileapp.views.profiles.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.FragmentProfilesBinding
import com.mglock.locationprofileapp.receiver.GeofenceBroadcastReceiver
import com.mglock.locationprofileapp.services.LocationUpdateService
import com.mglock.locationprofileapp.util.GeofenceManager
import com.mglock.locationprofileapp.util.LocationUtil
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.viewmodels.profiles.ProfilesViewModel
import com.mglock.locationprofileapp.views.profiles.activities.AddProfileActivity
import com.mglock.locationprofileapp.views.profiles.activities.EditProfileActivity
import com.mglock.locationprofileapp.views.profiles.adapter.RecyclerViewProfilesAdapter

@SuppressLint("UnspecifiedImmutableFlag")
class ProfilesFragment : Fragment() {

    private var _binding: FragmentProfilesBinding? = null
    private val binding get(): FragmentProfilesBinding = _binding!!
    private lateinit var mViewModel: ProfilesViewModel
    private lateinit var mGeofenceManager: GeofenceManager
    private lateinit var mGeofencingClient: GeofencingClient
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[ProfilesViewModel::class.java]
        mGeofencingClient = LocationServices.getGeofencingClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfilesBinding.inflate(inflater, container, false)

        // set the adapter for the recyclerview to display the list items
        val recyclerView = binding.recyclerViewProfiles
        val textViewNoProfiles = binding.textViewNoProfiles
        mViewModel.profiles.observe(viewLifecycleOwner){ profiles ->
            // if the view is empty, a text is shown instead
            if(profiles.isEmpty()){
                recyclerView.visibility = View.GONE
                textViewNoProfiles.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                textViewNoProfiles.visibility = View.GONE
                mViewModel.updateRelationsOfProfiles()
                recyclerView.adapter = RecyclerViewProfilesAdapter(profiles, mViewModel){ profile ->
                    clickEditButton(profile)
                }
            }
        }

        binding.fabAddProfile.setOnClickListener {
            val intent = Intent(context, AddProfileActivity::class.java)
            startActivity(intent)
        }

        binding.activateProfilesSwitch.isChecked = LocationUpdateService.isRunning
        binding.activateProfilesSwitch.setOnClickListener {
            val serviceAlreadyRunning = LocationUpdateService.isRunning
            val isChecked = binding.activateProfilesSwitch.isChecked
            if(isChecked && !serviceAlreadyRunning){
                // set switch to false until we are sure all necessary location requirements are met
                binding.activateProfilesSwitch.isChecked = false
                LocationUtil.checkCurrentLocationSettings(this){
                    binding.activateProfilesSwitch.isChecked = true
                    startProfiles()
                }
            }
            if (!isChecked && serviceAlreadyRunning){
                stopProfiles()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickEditButton(profile: ProfileWithRelations){
        val intent = Intent(context, EditProfileActivity::class.java)
        intent.putExtra("profile", profile)
        startActivity(intent)
    }

    private fun startProfiles() {
        // ask for general location permissions
        val profiles = mViewModel.profiles.value!!
        val activeProfiles = profiles.filter { profile -> profile.profile.active }
        if(activeProfiles.isNotEmpty()) {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(PermissionListener(requireContext()) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Start location tracking")
                        .setMessage(
                            "The location tracking will now be started. " +
                                    "Make sure that the app has access to the location " +
                                    "even when closed and not only while in use"
                        )
                        .setPositiveButton("Start") { _, _ ->
                            startService(activeProfiles)
                        }
                        .setNegativeButton("Don't start") { _, _ ->
                            // deactivate again if user denies
                            binding.activateProfilesSwitch.isChecked = false
                        }
                        .show()
                })
                .check()
        } else {
            binding.activateProfilesSwitch.isChecked = false
            AlertDialog.Builder(requireContext())
                .setTitle("No active profiles")
                .setMessage("There are no active profiles. Please activate at least one profile.")
                .setPositiveButton("Okay", null)
                .show()
        }
    }

    private fun startService(activeProfiles: List<ProfileWithRelations>){
        // ask for background permission separately
        // start the service to update location and the geofencing
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
            .withListener(PermissionListener(requireContext()){
                val serviceIntent = Intent(requireContext(), LocationUpdateService::class.java)
                ContextCompat.startForegroundService(requireContext(), serviceIntent)

                // start geofencing
                val activeProfilesWithPlace = activeProfiles.filter { profile -> profile.place != null }
                if(activeProfilesWithPlace.isNotEmpty()) {
                    mGeofenceManager = GeofenceManager()
                    mGeofenceManager.startGeofencing(
                        activeProfilesWithPlace,
                        mGeofencingClient,
                        geofencePendingIntent
                    )
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("No place triggers found")
                        .setMessage("There are no active profiles that are triggered by location.")
                        .setPositiveButton("Okay", null)
                        .show()
                }
            })
            .check()
    }

    private fun stopProfiles(){
        val serviceIntent = Intent(requireContext(), LocationUpdateService::class.java)
        requireActivity().stopService(serviceIntent)
        // stop geofencing
        mGeofenceManager.stopGeofencing(mGeofencingClient, geofencePendingIntent)
    }
}