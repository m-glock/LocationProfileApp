package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.TimePickerFragment
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

        // set dropdown values for place
        mViewModel.places.observe(viewLifecycleOwner){ places ->
            binding.addPlaceDropdown.adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                places.map { place -> place.title }
            )
        }

        // open dialog for adding actions to the profile
        binding.addActionButton.setOnClickListener {
            //TODO open modal to choose action
            binding.actionsListText.append("fcinwefvoinewpfovicnediovnwe, oviehwf, eoivhew, fewivh, efcius, fosieh")
        }

        // open a timepicker for start and end time
        binding.editTextTimeStart.setOnClickListener {
            TimePickerFragment(true, mViewModel).show(childFragmentManager, "timePicker")
        }
        mViewModel.timeStart.observe(viewLifecycleOwner){ timeStart ->
            binding.editTextTimeStart.setText(timeStart)
        }
        binding.editTextTimeEnd.setOnClickListener {
            TimePickerFragment(false, mViewModel).show(childFragmentManager, "timePicker")
        }
        mViewModel.timeEnd.observe(viewLifecycleOwner){ timeEnd ->
            binding.editTextTimeEnd.setText(timeEnd)
        }

        // get all input values and call the ViewModel's method to add the profile to the DB
        binding.addProfileButton.setOnClickListener {
            createProfileFromInput()
        }

        return binding.root
    }

    private fun createProfileFromInput(){
        //TODO
        val newProfile = Profile(0, "", null, null, false)
        mViewModel.addProfile(newProfile)
    }
}