package com.mglock.locationprofileapp.views.places.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.ActivityAddPlaceBinding
import com.mglock.locationprofileapp.views.places.fragments.AddPlaceAutoFragment
import com.mglock.locationprofileapp.views.places.fragments.AddPlaceManualFragment

class AddPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddPlace)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddPlace.setNavigationOnClickListener {
            onBackPressed()
        }

        val addPlaceManualFragment = AddPlaceManualFragment(null)
        val addPlaceAutoFragment = AddPlaceAutoFragment()
        setNewFragment(addPlaceManualFragment)

        binding.automaticModeButton.setOnClickListener {
            setNewFragment(addPlaceAutoFragment)
            changeButtons(binding.automaticModeButton, binding.manualModeButton)
        }

        binding.manualModeButton.setOnClickListener {
            setNewFragment(addPlaceManualFragment)
            changeButtons(binding.manualModeButton, binding.automaticModeButton)
        }
    }

    private fun setNewFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.modeFragment.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }

    private fun changeButtons(buttonFocused: Button, buttonUnfocused: Button){
        buttonUnfocused.setBackgroundResource(R.drawable.border_button)
        buttonUnfocused.setTextColor(ContextCompat.getColor(
            applicationContext,
            R.color.textColorDark
        ))
        buttonFocused.setBackgroundResource(R.drawable.filled_button)
        buttonFocused.setTextColor(ContextCompat.getColor(
            applicationContext,
            R.color.textColorLight
        ))
    }
}