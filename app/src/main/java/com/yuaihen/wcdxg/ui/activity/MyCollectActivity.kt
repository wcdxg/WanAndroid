package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMyCollectBinding
import com.yuaihen.wcdxg.ui.adapter.MyViewPagerAdapter
import com.yuaihen.wcdxg.ui.fragment.CollectFragment

/**
 * Created by Yuaihen.
 * on 2021/6/15
 * 我的收藏页面
 */
class MyCollectActivity : BaseActivity() {

    private lateinit var binding: ActivityMyCollectBinding
    private val adapter by lazy {
        MyViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }
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


    companion object {
        /**
         * 跳转到当前页面
         */
        fun start(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, MyCollectActivity::class.java).apply {
                bundle?.let { putExtras(it) }
            }
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        binding.apply {
            ivBack.setOnClickListener { finish() }
        }
    }

    override fun initData() {
        val titleList = mutableListOf<String>().apply {
            add(getString(R.string.collect_article))
            add(getString(R.string.collect_website))
        }
        adapter.addFragmentList(fragmentList, titleList)
        binding.apply {
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}