package com.mglock.locationprofileapp.views.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddPlaceManualBinding
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.viewmodels.AddPlaceManualViewModel

class AddPlaceManualFragment : Fragment() {
    private var _binding: FragmentAddPlaceManualBinding? = null
    private val binding get(): FragmentAddPlaceManualBinding = _binding!!
    private lateinit var mViewModel: AddPlaceManualViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var mLatitude: Double? = null
    private var mLongitude: Double? = null
    private var place: com.mglock.locationprofileapp.database.entities.Place? = null
    private var buttonText: String = "Add Place"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[AddPlaceManualViewModel::class.java]

        if(!Places.isInitialized()){
            val applicationInfo = requireContext().packageManager.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)
            val apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY")
            Places.initialize(requireContext(), apiKey!!)
        }

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val place: Place = Autocomplete.getPlaceFromIntent(data!!)

                _binding!!.editTextAddress.setText(place.address)
                mLatitude = place.latLng!!.latitude
                mLongitude = place.latLng!!.longitude
            } else {
                AlertDialog.Builder(context)
                    .setTitle("Error with Addresses")
                    .setMessage("Something went wrong the address search. " +
                            "Please try again later. If the problem persists, ask support.")
                    .setPositiveButton("Okay", null)
                    .create()
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPlaceManualBinding.inflate(inflater, container, false)

        // set values (if available)
        _binding!!.addPlaceButton.text = buttonText
        if(place != null){
            _binding!!.editTextTitleManual.setText(place!!.title)
            if(place!!.address.isNullOrBlank()){
                _binding!!.editTextAddress.setText(getString(R.string.lat_long_as_text, place!!.latitude, place!!.longitude))
            } else {
                _binding!!.editTextAddress.setText(place!!.address)
            }
        }

        _binding!!.editTextAddress.setOnClickListener { _ ->
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(PermissionListener(requireContext()){
                    //method to be called if permissions are granted
                   startGoogleAddress()
                })
                .check()
        }

        _binding!!.addPlaceButton.setOnClickListener {
            val newPlaceTitle = _binding!!.editTextTitleManual.text.toString()
            val address = _binding!!.editTextAddress.text.toString()
            if(address.isNotBlank() && newPlaceTitle.isNotBlank()){
                if(place != null){
                    place!!.title = newPlaceTitle
                    place!!.address = address
                    updatePlace(place!!)
                } else {
                    addPlace(newPlaceTitle, address)
                }
                requireActivity().finish()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("Missing Information")
                    .setMessage("Title and Address information is needed. Please make sure that both are set.")
                    .setPositiveButton("Okay", null)
                    .create()
                    .show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGoogleAddress(){
        try{
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(requireContext())
            resultLauncher.launch(intent)
        } catch(e: Exception){
            Log.i("Error", e.printStackTrace().toString())
        }
    }

    private fun addPlace(newPlaceTitle: String, address: String){
        mViewModel.addPlace(newPlaceTitle, address, mLatitude!!, mLongitude!!)
    }

    private fun updatePlace(place: com.mglock.locationprofileapp.database.entities.Place){
        mViewModel.updatePlace(place)
    }

    companion object{
        fun newInstance(buttonText: String, place: com.mglock.locationprofileapp.database.entities.Place): AddPlaceManualFragment{
            val fragment = AddPlaceManualFragment()
            fragment.buttonText = buttonText
            fragment.place = place
            return fragment
        }
    }
}