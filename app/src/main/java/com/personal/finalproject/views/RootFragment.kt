package com.personal.finalproject.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.personal.finalproject.R
import com.personal.finalproject.adapters.TabPagerAdapter
import com.personal.finalproject.databinding.FragmentRootBinding

class RootFragment : Fragment() {

    private var binding: FragmentRootBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentRootBinding.inflate(layoutInflater, container, false)
        setupBottomTabBar()
        return binding!!.root
    }

    private fun setupBottomTabBar(){
        val adapter = TabPagerAdapter(childFragmentManager, lifecycle)
        binding!!.pager.adapter = adapter
        binding!!.pager.offscreenPageLimit = adapter.itemCount - 1
        binding!!.pager.isUserInputEnabled = false
        binding!!.bottomNav.inflateMenu(R.menu.bottom_navigation_menu)

        binding!!.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_news_home -> binding!!.pager.currentItem = 0
                R.id.about_page -> binding!!.pager.currentItem = 1
            }
            true
        }


    }
}