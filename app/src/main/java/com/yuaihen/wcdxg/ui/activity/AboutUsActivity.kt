package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityAboutUsBinding
import com.yuaihen.wcdxg.utils.GlideUtil

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 关于我们页面
 */
class AboutUsActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AboutUsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getBindingView(): View {
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        GlideUtil.showImageViewBlur(binding.ivBg, R.drawable.ic_head_test)
    }
}