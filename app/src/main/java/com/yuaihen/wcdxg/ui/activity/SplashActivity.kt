package com.yuaihen.wcdxg.ui.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivitySplashBinding
import com.yuaihen.wcdxg.utils.SPUtils
import com.yuaihen.wcdxg.utils.UserUtil
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/4/28
 * 广告页面
 */
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun getBindingView(): View {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun initData() {
        SPUtils.clear()
        lifecycleScope.launch {
            if (UserUtil.getCookie().isNotEmpty()) {
                start2Activity(MainActivity::class.java, null, true)
            } else {
                start2Activity(LoginActivity::class.java, null, true)
            }
        }

    }

}