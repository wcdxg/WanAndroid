package com.yuaihen.wcdxg.ui.activity

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.leancloud.AVUser
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.AppManager
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.ActivityLoginBinding
import com.yuaihen.wcdxg.utils.*
import io.reactivex.disposables.Disposable


/**
 * Created by Yuaihen.
 * on 2021/3/27
 * 登录页面
 */
class LoginActivity : BaseActivity(), TextView.OnEditorActionListener {

    private lateinit var binding: ActivityLoginBinding

    override fun getBindingView(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.progressBar.invisible()
        binding.btnLogin.setOnClickListener {
            AppManager.getInstance().hideSoftKeyBoard(this)
            verificationAccountAndPwd()
        }
        binding.btnRegister.setOnClickListener {


        }
        binding.editTextVerificationCode.setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            verificationAccountAndPwd()
            return true
        }
        return false
    }

    override fun initData() {

    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColorTransform(android.R.color.white)
            .statusBarDarkFont(false)
            .fullScreen(true)
            .barAlpha(1f)
            .init()
    }

    private fun verificationAccountAndPwd() {
        val userName = binding.editTextAccount.text?.trim().toString()
        val pwd = binding.editTextPwd.text?.trim().toString()
        val verificationCode = binding.editTextVerificationCode.text.trim().toString()
        if (userName.isEmpty() || pwd.isEmpty() || verificationCode.isEmpty()) {
            toast(R.string.login_fail)
            return
        } else {
            loginByLeanCloud(userName, pwd)
        }
    }

    /**
     * 验证账号密码
     * 使用LeanCloud保存用户账号信息
     */
    private fun loginByLeanCloud(userName: String, pwd: String) {
        binding.progressBar.visible()
        AVUser.logIn(userName, pwd).subscribe(object : io.reactivex.Observer<AVUser> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: AVUser) {
                UserUtil.setUserIsLogin(true)
                toast(R.string.login_success)
                start2Activity(MainActivity::class.java, finish = true)
            }

            override fun onError(e: Throwable) {
                if (e.message == "Could not find user") {
                    toast("用户未注册")
                } else {
                    toast("登录失败 ${e.message}")
                }
                LogUtil.d("hello", "onError: ${e.message}")
                binding.progressBar.visibility = View.INVISIBLE
            }

            override fun onComplete() {

            }

        })
    }


}