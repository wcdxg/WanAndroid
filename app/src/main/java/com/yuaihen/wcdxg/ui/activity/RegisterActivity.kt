package com.yuaihen.wcdxg.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.leancloud.AVUser
import com.yuaihen.wcdxg.AppManager
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityRegisterBinding
import com.yuaihen.wcdxg.utils.UserUtil
import com.yuaihen.wcdxg.utils.invisible
import com.yuaihen.wcdxg.utils.visible
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Yuaihen.
 * on 2021/4/29
 * 用户注册
 */
class RegisterActivity : BaseActivity(), TextView.OnEditorActionListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun getBindingView(): View {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        binding.progressBar.invisible()
        binding.editTextPwd.setOnEditorActionListener(this)
        binding.btnRegister.setOnClickListener {
            verificationAccountAndPwd()
        }
        binding.editTextAccount.addTextChangedListener(watcher)
        binding.editTextPwd.addTextChangedListener(watcher)
        binding.tvBack.setOnClickListener { finish() }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            verificationAccountAndPwd()
            return true
        }
        return false
    }

    private fun verificationAccountAndPwd() {
        val userName = binding.editTextAccount.text?.trim().toString()
        val pwd = binding.editTextPwd.text?.trim().toString()
        if (userName.isEmpty() || pwd.isEmpty()) {
            toast(R.string.login_fail)
            return
        } else {
            registerUser(userName, pwd)
        }
    }

    private fun registerUser(name: String, pwd: String) {
        AVUser().apply {
            binding.progressBar.visible()
            username = name
            password = pwd
            signUpInBackground().subscribe(object : Observer<AVUser> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: AVUser) {
                    UserUtil.setUserIsLogin(true)
                    AppManager.getInstance().removeActivity(LoginActivity::class.java)
                    start2Activity(MainActivity::class.java, finish = true)
                }

                override fun onError(e: Throwable) {
                    toast("注册失败 ${e.message}")
                    binding.progressBar.invisible()
                }

                override fun onComplete() {
                }

            })
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val t1 = binding.editTextAccount.text.toString().trim().isNotEmpty()
            val t2 = binding.editTextPwd.text.toString().trim().isNotEmpty()
            binding.btnRegister.isEnabled = t1 and t2
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }
}