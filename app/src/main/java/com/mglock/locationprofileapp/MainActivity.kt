package com.mglock.locationprofileapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.mglock.locationprofileapp.databinding.ActivityMainBinding
import com.mglock.locationprofileapp.ui.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabTitles = arrayOf(
            getString(R.string.tab_title_actions),
            getString(R.string.tab_title_profiles),
            getString(R.string.tab_title_places)
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        viewPager.currentItem = 1
    }
}