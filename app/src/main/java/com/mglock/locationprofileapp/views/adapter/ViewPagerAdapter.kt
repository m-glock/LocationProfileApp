package com.mglock.locationprofileapp.views.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mglock.locationprofileapp.views.fragments.ActionsFragment
import com.mglock.locationprofileapp.views.fragments.PlacesFragment
import com.mglock.locationprofileapp.views.fragments.ProfilesFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ActionsFragment()
            1 -> return ProfilesFragment()
        }
        return PlacesFragment()
    }
}