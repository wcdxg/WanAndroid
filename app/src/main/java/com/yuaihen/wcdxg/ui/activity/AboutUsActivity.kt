package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityAboutUsBinding
import com.yuaihen.wcdxg.utils.ClipboardUtil

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
        binding.apply {
            tvGithubUrl.setOnClickListener {
                WebViewActivity.start(this@AboutUsActivity, tvGithubUrl.text.toString())
            }
            tvGithubUrl.setOnLongClickListener {
                ClipboardUtil.copyStrToClipboard(this@AboutUsActivity, tvGithubUrl.text.toString())
                toast("已复制到剪切板")
                true
            }
            tvJianshuUrl.setOnClickListener {
                WebViewActivity.start(this@AboutUsActivity, tvJianshuUrl.text.toString())
            }
            tvJianshuUrl.setOnLongClickListener {
                ClipboardUtil.copyStrToClipboard(this@AboutUsActivity, tvJianshuUrl.text.toString())
                toast("已复制到剪切板")
                true
            }
            tvQq.setOnClickListener {
                toast("已复制到剪切板")
                ClipboardUtil.copyStrToClipboard(this@AboutUsActivity, tvQq.text.toString())
            }
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}