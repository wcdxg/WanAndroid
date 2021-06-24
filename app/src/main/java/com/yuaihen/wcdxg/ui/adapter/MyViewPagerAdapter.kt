package com.yuaihen.wcdxg.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Yuaihen.
 * on 2021/5/31
 * ViewPager2 FragmentPagerAdapter
 */
class MyViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    private val fragmentList = mutableListOf<Fragment>()
    private val mTitleList = mutableListOf<String>()

    fun addFragmentList(list: List<Fragment>, titleList: List<String>? = null) {
        fragmentList.addAll(list)
        titleList?.let {
            mTitleList.addAll(it)
        }
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

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position < mTitleList.size) {
            return mTitleList[position]
        }
        return super.getPageTitle(position)
    }
}