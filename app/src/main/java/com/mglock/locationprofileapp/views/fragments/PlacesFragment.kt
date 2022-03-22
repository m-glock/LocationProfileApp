package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.databinding.FragmentPlacesBinding
import com.mglock.locationprofileapp.viewmodels.PlacesViewModel
import com.mglock.locationprofileapp.views.adapter.RecyclerViewPlacesAdapter

class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val binding get(): FragmentPlacesBinding = _binding!!
    private lateinit var mViewModel: PlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)

        // set the adapter for the recyclerview to display the list items
        val recyclerView = _binding!!.recyclerViewPlaces
        mViewModel.places.observe(viewLifecycleOwner){ places ->
            recyclerView.adapter = RecyclerViewPlacesAdapter(places)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}