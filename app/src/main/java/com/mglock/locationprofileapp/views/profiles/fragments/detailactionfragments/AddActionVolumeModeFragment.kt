package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddActionVolumeModeBinding
import com.mglock.locationprofileapp.util.phonefunctionality.AudioHandler

class AddActionVolumeModeFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionVolumeModeBinding? = null
    private val binding get(): FragmentAddActionVolumeModeBinding = _binding!!
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            binding.valueDropdown.setSelection(AudioHandler.VolumeModes.MODE_NORMAL.ordinal)
        }
    }

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

        binding.valueDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if(AudioHandler.VolumeModes.values()[pos] == AudioHandler.VolumeModes.MODE_SILENT){
                    val audioHandler = AudioHandler(requireContext())
                    if (!audioHandler.isDoNotDisturbAllowed()) {
                        showDoNotDisturbAlert()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return binding.root
    }

    private fun showDoNotDisturbAlert(){
        AlertDialog.Builder(context)
            .setTitle("Do not disturb mode not allowed")
            .setMessage("Your phone does not allow the app to set it to 'do not disturb' mode. " +
                    "Please change the settings for this app or choose another action.")
            .setPositiveButton("Settings"){ _, _ ->
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                resultLauncher.launch(intent)
            }
            .setNegativeButton("Change Action"){ _, _ ->
                binding.valueDropdown.setSelection(AudioHandler.VolumeModes.MODE_NORMAL.ordinal)
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String{
        return binding.valueDropdown.selectedItem as String
    }

    override fun getMode(): String? {
        return null
    }
}