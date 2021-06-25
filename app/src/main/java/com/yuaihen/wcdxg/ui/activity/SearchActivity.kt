package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivitySearchBinding

/**
 * Created by Yuaihen.
 * on 2021/6/25
 * 搜索页面
 */
class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun getBindingView(): View {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initData() {

    }
}