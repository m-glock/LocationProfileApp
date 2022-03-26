package com.mglock.locationprofileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mglock.locationprofileapp.databinding.ActivityAddPlaceBinding
import com.mglock.locationprofileapp.views.fragments.AddPlaceAutoFragment
import com.mglock.locationprofileapp.views.fragments.AddPlaceManualFragment

class AddPlace : AppCompatActivity() {

    lateinit var binding: ActivityAddPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val addPlaceManualFragment = AddPlaceManualFragment()
        val addPlaceAutoFragment = AddPlaceAutoFragment()
        setNewFragment(addPlaceManualFragment)

        binding.automaticModeButton.setOnClickListener {
            setNewFragment(addPlaceAutoFragment)
        }

        binding.manualModeButton.setOnClickListener {
            setNewFragment(addPlaceManualFragment)
        }
    }

    fun setNewFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.modeFragment.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}