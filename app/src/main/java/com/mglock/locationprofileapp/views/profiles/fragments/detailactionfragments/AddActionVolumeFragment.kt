package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddActionVolumeBinding
import com.mglock.locationprofileapp.util.phonefunctionality.AudioHandler

class AddActionVolumeFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionVolumeBinding? = null
    private val binding get(): FragmentAddActionVolumeBinding = _binding!!
    private lateinit var audioHandler: AudioHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionVolumeBinding.inflate(inflater, container, false)

        audioHandler = AudioHandler(requireContext())

        // set the min, max and current value of the slider
        val options = AudioHandler.VolumeStreamTypes.values()
        binding.valueDropdown.adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            options.map { option -> option.title }
        )

        // adapt max value of slider to selected stream type
        binding.valueDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val volumeStreamType = options[pos]
                binding.valueSlider.value = 0F
                val maxVal = audioHandler.getStreamMaxVolume(volumeStreamType.id)
                binding.valueSlider.valueTo = maxVal
                binding.sliderEndValue.text = maxVal.toInt().toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.valueSlider.addOnChangeListener { _, value, _ ->
            binding.valueText.text = value.toInt().toString()
        }

        // default max for slider for the first stream type in the dropdown
        binding.valueSlider.valueTo = audioHandler.getStreamMaxVolume(options[0].id)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String{
        return binding.valueSlider.value.toInt().toString()
    }

    override fun getMode(): String {
        return binding.valueDropdown.selectedItem as String
    }

}