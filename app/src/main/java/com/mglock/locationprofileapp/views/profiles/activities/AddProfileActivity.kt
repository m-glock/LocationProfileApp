package com.mglock.locationprofileapp.views.profiles.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mglock.locationprofileapp.views.profiles.fragments.AddProfileFragment
import com.mglock.locationprofileapp.databinding.ActivityAddProfileBinding

class AddProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddProfile)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddProfile.setNavigationOnClickListener {
            onBackPressed()
        }

        // open fragment to edit place
        val addProfileFragment = AddProfileFragment(null)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerAddProfile.id, addProfileFragment)
        fragmentTransaction.commit()
    }
}