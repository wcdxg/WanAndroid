package com.yuaihen.wcdxg.ui.activity

import android.view.View
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.AcitivityTestBinding
import com.yuaihen.wcdxg.utils.GlideUtil

/**
 * Created by Yuaihen.
 * on 2021/5/21
 */
class TestActivity : BaseActivity() {

    private lateinit var binding: AcitivityTestBinding

    override fun getBindingView(): View {
        binding = AcitivityTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        val url = "https://imagefile.xxlimageim.com/QN92fb8862dc2048779a5c92b77316ece9.png"
//        GlideUtil.showImageView(
//            binding.ivTest,
//            "http://imagefile.xxlimageim.com/QN2059960232700115.jpg"
////            "https://imagefile.xxlimageim.com/QN92fb8862dc2048779a5c92b77316ece9.png"
//        )

//        Glide.with(mContext).load(url).placeholder(R.drawable.ic_head_test).into(binding.ivTest);
        GlideUtil.showImageView(binding.ivTest, url)
    }

}