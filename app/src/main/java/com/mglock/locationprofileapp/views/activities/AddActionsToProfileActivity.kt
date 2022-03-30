package com.mglock.locationprofileapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.ActivityAddActionsToProfileBinding
import com.mglock.locationprofileapp.viewmodels.AddActionsToProfileViewModel
import com.mglock.locationprofileapp.views.adapter.RecyclerViewProfileActionsAdapter

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
            //TODO alert to user that data is lost, if confirmed, remove actions with profileId = null
            onBackPressed()
        }

        mViewModel.actions.observe(this){ detailActions ->
            binding.profileActionsRecyclerView.adapter =
                RecyclerViewProfileActionsAdapter(detailActions, mViewModel)
        }

        binding.fabAddActionToProfile.setOnClickListener {
            //TODO open up fragment/activity/dialog that lets you choose an action
        }

        binding.saveActionsButton.setOnClickListener {
            //TODO go back to profile and return current list of actions
        }
    }
}