package com.mglock.locationprofileapp.views.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.TimePickerFragment
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.FragmentAddProfileBinding
import com.mglock.locationprofileapp.util.enums.Weekday
import com.mglock.locationprofileapp.viewmodels.AddProfileViewModel
import java.util.Locale

class AddProfileFragment(private val editableProfile: ProfileWithRelations?) : Fragment() {

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
            val options = if(places.isEmpty()) emptyList()
                else places.map { place -> place.title }
            binding.addPlaceDropdown.adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                options
            )
        }

        // open dialog for adding actions to the profile
        binding.addActionButton.setOnClickListener {
            //TODO open modal to choose action
            binding.actionsListText.append("fcinwefvoinewpfovicnediovnwe, oviehwf, eoivhew, fewivh, efcius, fosieh")
        }
        mViewModel.actions.observe(viewLifecycleOwner){ actions ->
            val actionValuesText = actions.joinToString(", "){ detailAction ->
                detailAction.toString() //TODO profiledetailaction
            }
            binding.actionsListText.text = actionValuesText
        }

        // open a timepicker for start and end time and display time on screen
        binding.editTextTimeStart.setOnClickListener {
            TimePickerFragment(true, mViewModel).show(childFragmentManager, "timePicker")
        }
        mViewModel.timeStart.observe(viewLifecycleOwner){ timeStart ->
            binding.editTextTimeStart.setText(timeStart.toString())
        }
        binding.editTextTimeEnd.setOnClickListener {
            TimePickerFragment(false, mViewModel).show(childFragmentManager, "timePicker")
        }
        mViewModel.timeEnd.observe(viewLifecycleOwner){ timeEnd ->
            binding.editTextTimeEnd.setText(timeEnd.toString())
        }

        // get all input values and call the ViewModel's method to add the profile to the DB
        binding.addProfileButton.setOnClickListener {
            createProfileFromInput()
        }

        // set values (if available)
        setValues()
        if(editableProfile != null){
            mViewModel.profile.value = editableProfile
            mViewModel.buttonText.value = "Done"
        }

        return binding.root
    }

    private fun createProfileFromInput(){
        val useTimeframe = binding.checkBoxTime.isChecked
        val usePlace = binding.checkBoxPlace.isChecked
        val selectedPlaceTitle = binding.addPlaceDropdown.selectedItem as String

        // if any of the necessary fields are not set, display alert
        // else save data in DB and close Activity
        if(allInputsSet(usePlace, useTimeframe)){
            val title = binding.editTextTitleProfile.text.toString()
            if(mViewModel.profile.value != null) mViewModel.updateProfile(usePlace, useTimeframe, selectedPlaceTitle, getWeekdays())
            else mViewModel.addProfile(title, selectedPlaceTitle, getWeekdays(), useTimeframe, usePlace)
            requireActivity().finish()
        } else {
            AlertDialog.Builder(context)
                .setTitle("Missing Information")
                .setMessage("TODO")
                .setPositiveButton("Understood", null)
                .show()
        }
    }

    private fun allInputsSet(
        usePlace: Boolean,
        useTimeframe: Boolean
    ): Boolean{
        val atLeastOneChecked = usePlace || useTimeframe
        val isTitleSet = binding.editTextTitleProfile.text?.isNotBlank()
        val isTimeSet = if(useTimeframe) {
            mViewModel.timeStart.value != null && mViewModel.timeEnd.value != null
        } else useTimeframe
        return atLeastOneChecked && (isTitleSet ?: false || isTimeSet)
    }

    private fun getWeekdays(): Set<Weekday>{
        val weekdaySet: MutableSet<Weekday> = mutableSetOf()
        binding.weekdayLayout.forEach { child ->
            val checkbox = child as CheckBox
            if(checkbox.isChecked){
                val wU = checkbox.text.toString().uppercase(Locale.getDefault()).trim()
                weekdaySet.add(Weekday.valueOf(wU))
            }
        }
        return weekdaySet
    }

    private fun setValues(){
        mViewModel.buttonText.observe(viewLifecycleOwner){ text ->
            binding.addProfileButton.text = text
        }
        mViewModel.profile.observe(viewLifecycleOwner){ profile ->
            if(profile != null){
                binding.editTextTitleProfile.setText(profile.profile.title)
                if(profile.profile.placeId != null){
                    binding.addPlaceDropdown.setSelection(profile.profile.placeId!!.toInt())
                }
                if(profile.timeframe != null){
                    mViewModel.timeStart.value = profile.timeframe.from
                    mViewModel.timeEnd.value = profile.timeframe.to
                    profile.timeframe.weekdays.forEach { weekday ->
                        val checkbox = binding.root.findViewWithTag(weekday.title) as? CheckBox
                        checkbox?.isChecked = true
                    }
                }

                //TODO add actions
            }
        }
    }
}