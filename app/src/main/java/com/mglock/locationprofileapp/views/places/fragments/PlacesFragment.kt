package com.mglock.locationprofileapp.views.places.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.views.places.activities.AddPlaceActivity
import com.mglock.locationprofileapp.databinding.FragmentPlacesBinding
import com.mglock.locationprofileapp.viewmodels.places.PlacesViewModel
import com.mglock.locationprofileapp.views.places.activities.EditPlaceActivity
import com.mglock.locationprofileapp.views.places.adapter.RecyclerViewPlacesAdapter

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
        val recyclerView = binding.recyclerViewPlaces
        val textViewNoPlaces = binding.textViewNoPlaces
        mViewModel.places.observe(viewLifecycleOwner){ places ->
            // if the view is empty, a text is shown instead
            if(places.isEmpty()){
                recyclerView.visibility = View.GONE
                textViewNoPlaces.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                textViewNoPlaces.visibility = View.GONE
                recyclerView.adapter = RecyclerViewPlacesAdapter(
                    places,
                    mViewModel
                ) { place -> clickEditButton(place) }
            }
        }

        binding.fabAddPlace.setOnClickListener {
            val intent = Intent(context, AddPlaceActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickEditButton(place: Place) {
        val intent = Intent(context, EditPlaceActivity::class.java)
        intent.putExtra("place", place)
        startActivity(intent)
    }
}