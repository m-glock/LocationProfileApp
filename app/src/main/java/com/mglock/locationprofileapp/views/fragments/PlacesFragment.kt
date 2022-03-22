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
        val places = listOf(
            Place(0, "Zuhause", "Trojanstr. 2, 12437 Berlin", "324567", "76543", 5),
            Place(0, "Uni", "Wilhelminenhofstraße 75A, 12459 Berlin", "324567", "76543", 5),
            Place(0, "Arbeit", "Köpenicker Str. 9, 10997 Berlin", "324567", "76543", 5),
        )
        recyclerView.adapter = RecyclerViewPlacesAdapter(places)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}