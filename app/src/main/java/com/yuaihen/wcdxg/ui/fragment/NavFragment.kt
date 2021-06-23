package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentNavBinding
import com.yuaihen.wcdxg.ui.adapter.ViewPager2PagerAdapter

/**
 * Created by Yuaihen.
 * on 2021/6/18
 * 发现界面Fragment
 */
class NavFragment : BaseFragment() {

    private var _binding: FragmentNavBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { ViewPager2PagerAdapter(childFragmentManager, lifecycle) }
    private val fragmentList by lazy {
        mutableListOf<Fragment>().apply {
            add(CardFragment.newInstance(CardFragment.KNOWLEDGE_TREE))
            add(CardFragment.newInstance(CardFragment.PAGE_NAV))
            add(CardFragment.newInstance(CardFragment.OFFICIAL_ACCOUNTS))
            add(CardFragment.newInstance(CardFragment.PROJECT))
        }
    }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = FragmentNavBinding.inflate(inflater)
        return binding.root
    }

    override fun initData() {
        super.initData()
        adapter.addFragmentList(fragmentList)
        binding.apply {
            viewPager.adapter = adapter
            TabLayoutMediator(
                tabLayout, viewPager, true, true
            ) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.knowledge_system)
                    1 -> tab.text = getString(R.string.navigation)
                    2 -> tab.text = getString(R.string.official_accounts)
                    3 -> tab.text = getString(R.string.project)
                }
            }.attach()
        }
    }

    override fun unBindView() {
        _binding = null
    }
}