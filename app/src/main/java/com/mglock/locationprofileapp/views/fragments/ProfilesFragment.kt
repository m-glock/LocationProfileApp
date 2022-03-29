package com.mglock.locationprofileapp.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.FragmentProfilesBinding
import com.mglock.locationprofileapp.viewmodels.ProfilesViewModel
import com.mglock.locationprofileapp.views.activities.AddProfileActivity
import com.mglock.locationprofileapp.views.activities.EditPlaceActivity
import com.mglock.locationprofileapp.views.activities.EditProfileActivity
import com.mglock.locationprofileapp.views.adapter.RecyclerViewProfilesAdapter

class ProfilesFragment : Fragment() {

    private var _binding: FragmentProfilesBinding? = null
    private val binding get(): FragmentProfilesBinding = _binding!!
    private lateinit var mViewModel: ProfilesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[ProfilesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfilesBinding.inflate(inflater, container, false)

        // set the adapter for the recyclerview to display the list items
        val recyclerView = binding.recyclerViewProfiles
        val textViewNoProfiles = binding.textViewNoProfiles
        mViewModel.profiles.observe(viewLifecycleOwner){ profiles ->
            // if the view is empty, a text is shown instead
            if(profiles.isEmpty()){
                recyclerView.visibility = View.GONE
                textViewNoProfiles.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                textViewNoProfiles.visibility = View.GONE
                recyclerView.adapter = RecyclerViewProfilesAdapter(profiles, mViewModel){ profile ->
                    clickEditButton(profile)
                }
            }
        }

        binding.fabAddProfile.setOnClickListener {
            val intent = Intent(context, AddProfileActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun clickEditButton(profile: ProfileWithRelations){
        val intent = Intent(context, EditProfileActivity::class.java)
        intent.putExtra("profile", profile)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}