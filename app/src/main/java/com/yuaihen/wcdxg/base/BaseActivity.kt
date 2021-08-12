package com.yuaihen.wcdxg.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.gyf.immersionbar.ImmersionBar
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBackPressedListener
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.common.event.EventCode
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.ToastUtil


/**
 * Created by Yuaihen.
 * on 2020/10/28
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    protected open lateinit var mContext: Context
    private var layoutId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppManager.getInstance().addActivity(this);
        mContext = this
        if (getLayoutId() > 0) {
            setContentView(getLayoutId())
        } else {
            setContentView(getBindingView())
        }
        if (!NetworkUtils.isConnected()) {
            toast(getString(R.string.net_not_connected))
        }

        initImmersionBar()
        initBundle()
        initView()
        initListener()
        initData()
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentBar()
            .navigationBarAlpha(0f)
//            .autoDarkModeEnable(true)
            .init()
    }

    protected open fun getLayoutId(): Int {
        return layoutId
    }

    protected open fun initBundle() {}

    protected open fun initView() {}

    protected open fun initListener() {}

    abstract fun initData()

    open fun getBindingView(): View? {
        return null
    }

    protected open fun showDialog(waitMsg: String = getString(R.string.waiting)) {
        WaitDialog.show(waitMsg).onBackPressedListener =
            OnBackPressedListener {
                //return true自动关闭等待/提示对话框
                true
            }
        //自定义布局
//        WaitDialog.show(this)
//            .setCustomView(object : OnBindView<WaitDialog?>(R.layout.dialog_wait_view) {
//                override fun onBind(dialog: WaitDialog?, v: View) {
//
//                }
//
//            }).setOnBackPressedListener { true }
    }

    protected open fun hideLoading() {
        WaitDialog.dismiss()
    }

    protected open fun logD(msg: String) {
        LogUtil.d(javaClass.simpleName, msg)
    }

    protected open fun logD(tag: String?, msg: String) {
        LogUtil.d(tag, msg)
    }

    protected open fun logE(msg: String) {
        LogUtil.e(javaClass.simpleName, msg)
    }

    protected open fun toast(msg: String) {
        runOnUiThread { ToastUtil.show(msg) }
    }

    protected open fun toast(resId: Int) {
        runOnUiThread {
            ToastUtil.show(resId)
        }
    }

    override fun onStop() {
        super.onStop()
        hideLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
//        AppManager.getInstance().hideSoftKeyBoard(this)
//        AppManager.getInstance().removeActivity(this);
    }

    inline fun <reified T : BaseActivity> start2Activity(
        cls: Class<T>,
        bundle: Bundle? = null,
        finish: Boolean = false
    ) {
        val intent = Intent(this, cls)
        bundle?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
        if (finish) finish()
    }
}