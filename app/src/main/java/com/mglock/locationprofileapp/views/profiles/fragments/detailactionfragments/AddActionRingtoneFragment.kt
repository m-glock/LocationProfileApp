package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.FragmentAddActionRingtoneBinding
import com.mglock.locationprofileapp.util.phonefunctionality.AudioHandler

class AddActionRingtoneFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionRingtoneBinding? = null
    private val binding get(): FragmentAddActionRingtoneBinding = _binding!!
    private var ringtoneURL: String? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var audioHandler: AudioHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if(data != null){
                    val uri: Uri? = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                    val ringtone = RingtoneManager.getRingtone(requireContext(), uri)
                    val ringtoneTitle = ringtone.getTitle(requireContext())
                    binding.valueSelected.text = ringtoneTitle
                    if (uri != null) ringtoneURL = uri.toString()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionRingtoneBinding.inflate(inflater, container, false)

        audioHandler = AudioHandler(requireContext())

        binding.ringtoneTypeDropdown.adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            AudioHandler.RingtoneTypes.values().map { type -> type.title }
        )

        binding.chooseRingtoneButton.setOnClickListener {
            audioHandler.chooseNewRingtone(resultLauncher)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String {
        return ringtoneURL ?: ""
    }

    override fun getMode(): String {
        return binding.ringtoneTypeDropdown.selectedItem as String
    }

}