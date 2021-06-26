package com.yuaihen.wcdxg.ui.activity

import android.view.View
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.AcitivityTestBinding

/**
 * Created by Yuaihen.
 * on 2021/6/26
 */
class TestActivity : BaseActivity() {

    private lateinit var binding: AcitivityTestBinding
    override fun initData() {

    }

    override fun getBindingView(): View? {
        binding = AcitivityTestBinding.inflate(layoutInflater)
        return binding.root
    }
}