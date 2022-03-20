package com.mglock.locationprofileapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mglock.locationprofileapp.ui.fragments.ActionsFragment
import com.mglock.locationprofileapp.ui.fragments.PlacesFragment
import com.mglock.locationprofileapp.ui.fragments.ProfilesFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ProfilesFragment()
            1 -> return PlacesFragment()
        }
        return ActionsFragment()
    }
}