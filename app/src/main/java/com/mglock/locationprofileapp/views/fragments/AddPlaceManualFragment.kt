package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.FragmentAddPlaceManualBinding
import com.mglock.locationprofileapp.viewmodels.AddPlaceManualViewModel

class AddPlaceManualFragment : Fragment() {
    private var _binding: FragmentAddPlaceManualBinding? = null
    private val binding get(): FragmentAddPlaceManualBinding = _binding!!
    private lateinit var mViewModel: AddPlaceManualViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[AddPlaceManualViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPlaceManualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}