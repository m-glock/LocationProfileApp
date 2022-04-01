package com.mglock.locationprofileapp.views.profiles.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddActionVolumeModeBinding
import com.mglock.locationprofileapp.util.phonefunctionality.AudioHandler

class AddActionVolumeModeFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionVolumeModeBinding? = null
    private val binding get(): FragmentAddActionVolumeModeBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionVolumeModeBinding.inflate(inflater, container, false)

        // set the adapter for the dropdown
        binding.valueDropdown.adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            AudioHandler.VolumeModes.values().map { mode -> mode.title }
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String{
        return binding.valueDropdown.selectedItem as String
    }
}