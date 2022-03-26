package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.Constants
import com.mglock.locationprofileapp.databinding.FragmentAddPlaceAutoBinding
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

        val addPlaceTimesAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Constants.automaticPlaceTime
        )
        _binding!!.timeAutoCompleteTextView.setAdapter(addPlaceTimesAdapter)

        _binding!!.startAutomaticMode.setOnClickListener {
            //TODO check permissions
            val newPlaceTitle = _binding!!.editTextTitleAuto.text.toString()
            val locationServiceDuration = _binding!!.timeAutoCompleteTextView.text.toString()
            mViewModel.startLocationTracking(newPlaceTitle, locationServiceDuration)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}