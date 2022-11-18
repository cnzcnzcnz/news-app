package com.personal.finalproject.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.personal.finalproject.views.AboutFragment
import com.personal.finalproject.views.NewsHomeFragment

class TabPagerAdapter(fragmentManager: FragmentManager, lifeCycle:Lifecycle): FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> NewsHomeFragment()
            1 -> AboutFragment()
            else -> NewsHomeFragment()
        }
    }
}