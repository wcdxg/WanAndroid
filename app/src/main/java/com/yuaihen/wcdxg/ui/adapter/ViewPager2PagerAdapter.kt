package com.yuaihen.wcdxg.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by Yuaihen.
 * on 2021/5/31
 * ViewPager2 FragmentPagerAdapter
 */
class ViewPager2PagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = mutableListOf<Fragment>()

    fun addFragmentList(list: List<Fragment>) {
        fragmentList.addAll(list)
        notifyDataSetChanged()
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun removeFragment(fragment: Fragment) {
        fragmentList.remove(fragment)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}