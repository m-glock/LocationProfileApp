package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.databinding.FragmentAddProfileBinding
import com.mglock.locationprofileapp.viewmodels.AddProfileViewModel

class AddProfileFragment(private val profile: Profile?) : Fragment() {

    private var _binding: FragmentAddProfileBinding? = null
    private val binding get(): FragmentAddProfileBinding = _binding!!
    private lateinit var mViewModel: AddProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[AddProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddProfileBinding.inflate(inflater, container, false)

        mViewModel.places.observe(viewLifecycleOwner){ places ->
            binding.addPlaceDropdown.adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                places.map { place -> place.title }
            )
        }

        binding.addActionButton.setOnClickListener {
            //TODO open modal to choose action
            binding.actionsListText.append("Test, ")
        }

        binding.addTimeframeButton.setOnClickListener {
            //TODO open modal to choose timeframe
            binding.timeframesListText.append("Yay, ")
        }

        return binding.root
    }
}