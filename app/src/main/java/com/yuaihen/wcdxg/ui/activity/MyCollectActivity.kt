package com.yuaihen.wcdxg.ui.activity

import android.view.View
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMyCollectBinding

/**
 * Created by Yuaihen.
 * on 2021/6/15
 * 我的收藏页面
 */
class MyCollectActivity : BaseActivity() {

    private lateinit var binding: ActivityMyCollectBinding

    override fun getBindingView(): View {
        binding = ActivityMyCollectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        binding.ivBack.setOnClickListener { finish() }
    }
}