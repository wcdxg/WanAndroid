package com.yuaihen.policeinfo.ui.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.policeinfo.AppManager
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.base.BaseActivity
import com.yuaihen.policeinfo.databinding.ActivityLoginBinding
import com.yuaihen.policeinfo.utils.invisible
import com.yuaihen.policeinfo.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Yuaihen.
 * on 2021/3/27
 * 登录页面
 */
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun getBindingView(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.progressBar.invisible()
        binding.btnLogin.setOnClickListener {
            AppManager.getInstance().hideSoftKeyBoard(this)
            if (verificationAccountAndPwd()) {
                binding.progressBar.visible()
                lifecycleScope.launch {
                    delay(2000)
                    start2Activity(MainActivity::class.java, finish = true)
                }
            }
        }
    }

    override fun initData() {
        binding.editTextPwd
        binding.ivVerificationCode
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColorTransform(android.R.color.white)
            .statusBarDarkFont(true)
            .navigationBarColorTransform(android.R.color.white)
            .fullScreen(true)
            .barAlpha(1f)
            .init()
    }

    private fun verificationAccountAndPwd(): Boolean {
        val account = binding.editTextAccount.text.trim().toString()
        val pwd = binding.editTextPwd.text.trim().toString()
        val verificationCode = binding.editTextVerificationCode.text.trim().toString()
        return if (account.isEmpty() || pwd.isEmpty() || verificationCode.isEmpty()) {
            toast(R.string.login_fail)
            false
        } else {
            toast(R.string.login_success)
            true
        }
    }

}