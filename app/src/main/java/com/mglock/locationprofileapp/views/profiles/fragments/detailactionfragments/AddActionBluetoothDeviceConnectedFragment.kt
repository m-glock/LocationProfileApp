package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddActionBluetoothDeviceConnectedBinding
import com.mglock.locationprofileapp.util.phonefunctionality.BluetoothHandler
import com.mglock.locationprofileapp.views.profiles.fragments.BaseDetailActionFragment

class AddActionBluetoothDeviceConnectedFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionBluetoothDeviceConnectedBinding? = null
    private val binding get(): FragmentAddActionBluetoothDeviceConnectedBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionBluetoothDeviceConnectedBinding.inflate(inflater, container, false)

        val bluetoothHandler = BluetoothHandler(requireContext())
        val options = bluetoothHandler.getBondedBluetoothDeviceNames().toTypedArray()
        binding.deviceDropdown.adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            options
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String {
        return binding.deviceDropdown.selectedItem.toString()
    }

    override fun getMode(): String? {
        TODO("Not yet implemented")
    }
}