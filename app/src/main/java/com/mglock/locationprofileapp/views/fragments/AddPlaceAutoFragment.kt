package com.mglock.locationprofileapp.views.fragments

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.databinding.FragmentAddPlaceAutoBinding
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.viewmodels.AddPlaceAutoViewModel

class AddPlaceAutoFragment : Fragment() {

    private var _binding: FragmentAddPlaceAutoBinding? = null
    private val binding get(): FragmentAddPlaceAutoBinding = _binding!!
    private lateinit var mViewModel: AddPlaceAutoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[AddPlaceAutoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPlaceAutoBinding.inflate(inflater, container, false)

        _binding!!.startAutomaticMode.setOnClickListener {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(PermissionListener(requireContext()){
                    //method to be called if permissions are granted
                    startAutomaticLocationTracking()
                })
                .check()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startAutomaticLocationTracking(){
        val newPlaceTitle = _binding!!.editTextTitleAuto.text.toString()
        val radioGroup = _binding!!.radioGroupAddPlaceAutoTime
        val checkedRadioButton: RadioButton? = radioGroup.findViewById(radioGroup.checkedRadioButtonId)
        if(checkedRadioButton != null && newPlaceTitle.isNotBlank()){
            val locationServiceDuration = checkedRadioButton.text.toString()
            mViewModel.startLocationTracking(newPlaceTitle, locationServiceDuration)
            requireActivity().finish()
        } else {
            AlertDialog.Builder(context)
                .setTitle("Missing Information")
                .setMessage("Title and duration information is needed. Please make sure that both are set.")
                .setPositiveButton("Okay", null)
                .create()
                .show()
        }
    }
}