package com.mglock.locationprofileapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.databinding.ActivityEditPlaceBinding
import com.mglock.locationprofileapp.views.fragments.AddPlaceManualFragment

class EditPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMain.setNavigationOnClickListener {
            onBackPressed()
        }

        val place = intent.getSerializableExtra("place") as Place

        // open fragment to edit place
        val editFragment = AddPlaceManualFragment(place)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerEditPlace.id, editFragment)
        fragmentTransaction.commit()
    }
}