package com.yuaihen.wcdxg.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.blankj.utilcode.util.KeyboardUtils
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityLoginBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.LoginRegisterViewModel
import com.yuaihen.wcdxg.utils.UserUtil
import com.yuaihen.wcdxg.utils.invisible
import com.yuaihen.wcdxg.utils.visible


/**
 * Created by Yuaihen.
 * on 2021/3/27
 * 登录页面
 */
class LoginActivity : BaseActivity(), TextView.OnEditorActionListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginRegisterViewModel>()

    override fun getBindingView(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun initListener() {
        loginViewModel.loadingLiveData.observe(this) {
            if (it) binding.progressBar.visible() else binding.progressBar.invisible()
        }
        loginViewModel.errorLiveData.observe(this) {
            toast(it)
        }
        loginViewModel.loginLiveData.observe(this) {
            if (it) {
                toast(R.string.login_success)
                UserUtil.setLoginStatus(true)
                UserUtil.setUserName(userName)
                start2Activity(MainActivity::class.java, finish = true)
            }
        }
    }

    override fun initData() {
        binding.progressBar.invisible()
        binding.btnLogin.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
            verificationAccountAndPwd()
        }
        binding.btnRegister.setOnClickListener {
            start2Activity(RegisterActivity::class.java)
        }
        binding.editTextAccount.addTextChangedListener(watcher)
        binding.editTextPwd.addTextChangedListener(watcher)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            verificationAccountAndPwd()
            return true
        }
        return false
    }


    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val t1 = binding.editTextAccount.text.toString().trim().isNotEmpty()
            val t2 = binding.editTextPwd.text.toString().trim().isNotEmpty()
            binding.btnLogin.isEnabled = t1 and t2
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private var userName: String = ""
    private fun verificationAccountAndPwd() {
        userName = binding.editTextAccount.text?.trim().toString()
        val pwd = binding.editTextPwd.text?.trim().toString()
        if (userName.isEmpty() || pwd.isEmpty()) {
            toast(R.string.login_fail)
            return
        } else {
            login(userName, pwd)
        }
    }

    /**
     * 验证账号密码
     * 使用LeanCloud保存用户账号信息
     */
    private fun login(userName: String, pwd: String) {
        loginViewModel.login(userName, pwd)
    }


}