package com.yuaihen.wcdxg.ui.activity

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMyCollectBinding
import com.yuaihen.wcdxg.ui.adapter.ViewPager2PagerAdapter
import com.yuaihen.wcdxg.ui.fragment.CollectFragment

/**
 * Created by Yuaihen.
 * on 2021/6/15
 * 我的收藏页面
 */
class MyCollectActivity : BaseActivity() {

    private lateinit var binding: ActivityMyCollectBinding
    private val adapter by lazy { ViewPager2PagerAdapter(supportFragmentManager, lifecycle) }
    private val fragmentList by lazy {
        mutableListOf<Fragment>().apply {
            add(CollectFragment.newInstance(CollectFragment.COLLECT_ARTICLE_ARTICLE))
            add(CollectFragment.newInstance(CollectFragment.COLLECT_ARTICLE_WEBSITE))
        }
    }

    override fun getBindingView(): View {
        binding = ActivityMyCollectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        super.initView()
        binding.apply {
            ivBack.setOnClickListener { finish() }
        }
    }

    override fun initData() {
        adapter.addFragmentList(fragmentList)
        binding.apply {
            viewPager.adapter = adapter
            TabLayoutMediator(
                tabLayout, viewPager, true, true
            ) { tab, position ->
                if (position == 0) {
                    tab.text = getString(R.string.collect_article)
                } else {
                    tab.text = getString(R.string.collect_website)
                }
            }.attach()
        }

    }
}