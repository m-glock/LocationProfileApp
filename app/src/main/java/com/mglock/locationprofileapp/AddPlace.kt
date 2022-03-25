package com.mglock.locationprofileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mglock.locationprofileapp.databinding.ActivityAddPlaceBinding

class AddPlace : AppCompatActivity() {

    lateinit var binding: ActivityAddPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}