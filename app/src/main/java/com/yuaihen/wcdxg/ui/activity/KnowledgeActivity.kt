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
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.ActivityKnowledgeBinding
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.ui.adapter.MyViewPagerAdapter
import com.yuaihen.wcdxg.ui.fragment.ArticleFragment

/**
 * Created by Yuaihen.
 * on 2021/6/24
 * 知识体系文章界面
 */
class KnowledgeActivity : BaseActivity() {

    private lateinit var binding: ActivityKnowledgeBinding
    private val adapter by lazy {
        MyViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }
    private val fragmentList by lazy {
        mutableListOf<Fragment>()
    }
    private val titleList by lazy { mutableListOf<String>() }

    override fun getBindingView(): View {
        binding = ActivityKnowledgeBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun start(context: Context, bundle: Bundle?) {
            val intent = Intent(context, KnowledgeActivity::class.java).apply {
                bundle?.let {
                    putExtras(it)
                }
            }
            context.startActivity(intent)
        }
    }

    private var selectTab = 0
    override fun initData() {
        //获取知识体系传递过来的bundle和position
        intent.apply {
            val data = getParcelableExtra<KnowLedgeTreeModel.Data>(Constants.KNOWLEDGE_LABEL)
            selectTab = getIntExtra(Constants.POSITION, 0)

            data?.let {
                createTab(it)
            }
        }
    }


    private fun createTab(data: KnowLedgeTreeModel.Data) {
        binding.titleView.setTitle(data.name)
        data.children.forEach { childrenData ->
            fragmentList.add(ArticleFragment.create(childrenData.id))
            titleList.add(childrenData.name)
        }

        adapter.addFragmentList(fragmentList, titleList)
        binding.apply {
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 2
            tabLayout.setupWithViewPager(viewPager)
        }

        binding.viewPager.setCurrentItem(selectTab, false)
    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}