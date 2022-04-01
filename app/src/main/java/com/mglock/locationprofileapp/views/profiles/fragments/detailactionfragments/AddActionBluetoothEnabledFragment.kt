package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mglock.locationprofileapp.databinding.FragmentAddActionBluetoothEnabledBinding
import com.mglock.locationprofileapp.views.profiles.fragments.BaseDetailActionFragment

class AddActionBluetoothEnabledFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionBluetoothEnabledBinding? = null
    private val binding get(): FragmentAddActionBluetoothEnabledBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionBluetoothEnabledBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String {
        return if(binding.switchEnable.isChecked) "enabled" else "disabled"
    }
}