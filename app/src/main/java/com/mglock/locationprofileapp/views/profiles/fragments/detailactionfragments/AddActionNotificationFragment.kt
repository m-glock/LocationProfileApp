package com.mglock.locationprofileapp.views.profiles.fragments.detailactionfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mglock.locationprofileapp.databinding.FragmentAddActionNotificationBinding
import com.mglock.locationprofileapp.views.profiles.fragments.BaseDetailActionFragment

class AddActionNotificationFragment : Fragment(), BaseDetailActionFragment {

    private var _binding: FragmentAddActionNotificationBinding? = null
    private val binding get(): FragmentAddActionNotificationBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddActionNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getValue(): String{
        return binding.editTextNotification.text.toString()
    }

    override fun getMode(): String? {
        return null
    }
}