package com.mglock.locationprofileapp.views.profiles.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.AddActionValueDropdownFragmentBinding

class AddActionValueDropdownFragment(private val options: List<String>) : Fragment() {

    private var _binding: AddActionValueDropdownFragmentBinding? = null
    private val binding get(): AddActionValueDropdownFragmentBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = AddActionValueDropdownFragmentBinding.inflate(inflater, container, false)

        // set the adapter for the dropdown
        binding.valueDropdown.adapter = ArrayAdapter(
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

    fun getDropdownValue(): String{
        return binding.valueDropdown.selectedItem as String
    }
}