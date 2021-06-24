package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentNavBinding
import com.yuaihen.wcdxg.ui.adapter.MyViewPagerAdapter

/**
 * Created by Yuaihen.
 * on 2021/6/18
 * 发现界面Fragment
 */
class NavFragment : BaseFragment() {

    private var _binding: FragmentNavBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        MyViewPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }
    private val fragmentList by lazy {
        mutableListOf<Fragment>().apply {
            add(FindFragment.newInstance(FindFragment.KNOWLEDGE_TREE))
            add(FindFragment.newInstance(FindFragment.PAGE_NAV))
            add(FindFragment.newInstance(FindFragment.OFFICIAL_ACCOUNTS))
            add(FindFragment.newInstance(FindFragment.PROJECT))
        }
    }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = FragmentNavBinding.inflate(inflater)
        return binding.root
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        val titleList = mutableListOf<String>().apply {
            add(getString(R.string.knowledge_system))
            add(getString(R.string.navigation))
            add(getString(R.string.official_accounts))
            add(getString(R.string.project))
        }
        adapter.addFragmentList(fragmentList, titleList)
        binding.apply {
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun unBindView() {
        _binding = null
    }
}