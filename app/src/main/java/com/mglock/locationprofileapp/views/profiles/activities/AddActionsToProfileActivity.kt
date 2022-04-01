package com.mglock.locationprofileapp.views.profiles.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.ActivityAddActionsToProfileBinding
import com.mglock.locationprofileapp.viewmodels.profiles.AddActionsToProfileViewModel
import com.mglock.locationprofileapp.views.profiles.adapter.RecyclerViewProfileActionsAdapter
import com.mglock.locationprofileapp.views.profiles.fragments.AddDetailActionFragment

class AddActionsToProfileActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddActionsToProfileBinding
    private lateinit var mViewModel: AddActionsToProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddActionsToProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[AddActionsToProfileViewModel::class.java]

        val profileId = intent.getLongExtra("profileId", -1)
        mViewModel.getActions(profileId)

        setSupportActionBar(binding.toolbarAddActionsToProfile)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddActionsToProfile.setNavigationOnClickListener {
            onBackPressed()
        }

        mViewModel.actions.observe(this){ detailActions ->
            binding.profileActionsRecyclerView.adapter =
                RecyclerViewProfileActionsAdapter(detailActions, mViewModel, baseContext)
        }

        binding.fabAddActionToProfile.setOnClickListener {
            AddDetailActionFragment(profileId).show(supportFragmentManager, "Add Detail Action")
        }
    }
}