package com.example.reusablecomponents.tablayoutsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reusablecomponents.databinding.FragmentTabExampleBinding
import com.example.reusablecomponents.utils.BaseFragment
import com.example.viewpageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TabExampleFragment : BaseFragment<FragmentTabExampleBinding>() {


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTabExampleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    private fun setUpViewPager() = requireBinding {

        val adapter = ViewPagerAdapter(this@TabExampleFragment)
        adapter.addFragment(First(), "First")
        adapter.addFragment(Second(), "Second")

        viewPager2.adapter = adapter
        TabLayoutMediator(
            tabLayout,
            viewPager2
        ) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}