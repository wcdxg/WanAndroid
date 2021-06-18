package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityAppSettingBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.MyViewModel
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.utils.CleanDataUtils
import com.yuaihen.wcdxg.utils.DialogUtil
import com.yuaihen.wcdxg.utils.UserUtil

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 系统设置页面
 */
class AppSettingActivity : BaseActivity() {

    private lateinit var binding: ActivityAppSettingBinding
    private val mDialogUtil by lazy { DialogUtil() }
    private val viewModel by viewModels<MyViewModel>()

    override fun getBindingView(): View {
        binding = ActivityAppSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AppSettingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initListener() {
        super.initListener()
        viewModel.apply {
            logoutSuccess.observe(this@AppSettingActivity) { logoutSuccess ->
                if (logoutSuccess) {
                    //退回到登录页面
                    start2Activity(LoginActivity::class.java, finish = true)
                }
            }
            errorLiveData.observe(this@AppSettingActivity) {
                toast(it)
            }
        }

        binding.apply {
            tvExitLogin.setOnClickListener {
                mDialogUtil.showExitLoginDialog(this@AppSettingActivity) {
                    //清除保存的Cookie信息
                    ApiManage.getCookieJar().clear()
                    UserUtil.setLoginStatus(false)
                    viewModel.logout()
                }
            }
            switchColorMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    UserUtil.setDarkMode(true)
                } else {
                    UserUtil.setDarkMode(false)
                }
            }
            clClearCache.setOnClickListener {
                mDialogUtil.clearCacheDialog(this@AppSettingActivity) {
                    //清除缓存
                    CleanDataUtils.clearAllCache(this@AppSettingActivity)
                    binding.tvCacheSize.text =
                        CleanDataUtils.getTotalCacheSize(this@AppSettingActivity)
                    toast(R.string.clear_cache_success)
                }
            }

        }
    }

    override fun initData() {
        binding.tvCacheSize.text = CleanDataUtils.getTotalCacheSize(this)
    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}