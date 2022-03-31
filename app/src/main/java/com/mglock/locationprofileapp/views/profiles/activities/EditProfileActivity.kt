package com.mglock.locationprofileapp.views.profiles.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.ActivityEditProfileBinding
import com.mglock.locationprofileapp.views.profiles.fragments.AddProfileFragment

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarEditProfile)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarEditProfile.setNavigationOnClickListener {
            onBackPressed()
        }

        val profile = intent.getSerializableExtra("profile") as ProfileWithRelations

        // open fragment to edit place
        val editFragment = AddProfileFragment(profile)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerEditProfile.id, editFragment)
        fragmentTransaction.commit()
    }
}