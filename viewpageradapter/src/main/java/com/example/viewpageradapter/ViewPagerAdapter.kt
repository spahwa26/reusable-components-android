package com.example.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter : FragmentStateAdapter {
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    constructor(fragment: Fragment) : super(fragment)

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    fun getPageTitle(position: Int): String {
        return fragmentTitleList[position]
    }

    fun clear() {
        fragmentList.clear()
        fragmentTitleList.clear()
    }
}