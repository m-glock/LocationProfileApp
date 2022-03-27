package com.mglock.locationprofileapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.databinding.ActivityAddPlaceBinding
import com.mglock.locationprofileapp.views.fragments.AddPlaceAutoFragment
import com.mglock.locationprofileapp.views.fragments.AddPlaceManualFragment

class AddPlaceActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAddPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        setSupportActionBar(_binding.toolbarMain)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Add a new place"
        _binding.toolbarMain.setNavigationOnClickListener {
            onBackPressed()
        }

        val addPlaceManualFragment = AddPlaceManualFragment()
        val addPlaceAutoFragment = AddPlaceAutoFragment()
        setNewFragment(addPlaceManualFragment)

        _binding.automaticModeButton.setOnClickListener {
            setNewFragment(addPlaceAutoFragment)
            changeButtons(_binding.automaticModeButton, _binding.manualModeButton)
        }

        _binding.manualModeButton.setOnClickListener {
            setNewFragment(addPlaceManualFragment)
            changeButtons(_binding.manualModeButton, _binding.automaticModeButton)
        }
    }

    private fun setNewFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(_binding.modeFragment.id, fragment)
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