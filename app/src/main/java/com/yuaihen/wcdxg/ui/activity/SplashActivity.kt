package com.yuaihen.wcdxg.ui.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivitySplashBinding
import com.yuaihen.wcdxg.utils.PermissionUtils
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

    override fun initListener() {
        //申请运行权限
        PermissionUtils.requestAppPermission(this) { allGranted: Boolean, deniedList: List<String?> ->
            if (allGranted) {
                //权限授予成功
                login()
            } else {
                toast("权限被拒绝，请到设置中手动开启")
                finish()
            }

        }
    }

    private fun login() {
        lifecycleScope.launch {
            if (UserUtil.getLoginStatus()) {
                start2Activity(MainActivity::class.java, null, true)
            } else {
                start2Activity(LoginActivity::class.java, null, true)
            }
        }
    }

    override fun initData() {

    }

}