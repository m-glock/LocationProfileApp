package com.mglock.locationprofileapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.ActivityAddActionsToProfileBinding
import com.mglock.locationprofileapp.viewmodels.AddActionsToProfileViewModel
import com.mglock.locationprofileapp.views.adapter.RecyclerViewProfileActionsAdapter
import com.mglock.locationprofileapp.views.fragments.AddDetailActionFragment

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
            AlertDialog.Builder(this)
                .setTitle("Careful!")
                .setMessage("If you return to the profile screen without saving your data will be lost.")
                .setPositiveButton("Go back"){ _, _ ->
                    mViewModel.removeAllActionsWithNoProfile()
                    onBackPressed()
                }.setNegativeButton("Stay here", null)
                .create()
        }

        mViewModel.actions.observe(this){ detailActions ->
            binding.profileActionsRecyclerView.adapter =
                RecyclerViewProfileActionsAdapter(detailActions, mViewModel)
        }

        binding.fabAddActionToProfile.setOnClickListener {
            AddDetailActionFragment().show(supportFragmentManager, "Add Detail Action")
        }

        binding.saveActionsButton.setOnClickListener {
            //TODO go back to profile and return current list of actions
        }
    }
}