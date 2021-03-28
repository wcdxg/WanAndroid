package com.yuaihen.policeinfo.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import com.blankj.utilcode.util.NetworkUtils
import com.gyf.immersionbar.ImmersionBar
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBackPressedListener
import com.lyt.livedatabus.LiveDataBus
import com.yuaihen.policeinfo.AppManager
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.common.event.EventCode
import com.yuaihen.policeinfo.utils.LogUtil
import com.yuaihen.policeinfo.utils.ToastUtil


/**
 * Created by Yuaihen.
 * on 2020/10/28
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    protected open lateinit var mContext: Context
    private var layoutId = 0

    //Activity切换动画
    enum class TransitionMode {
        DEFAULT, LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initTransitionMode()
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this);
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

        //监听Event事件
        LiveDataBus.get<String>(EventCode.FINISH_ALL)
            .observe(this) {
                finish()
            }
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColorTransform(android.R.color.white)
            .statusBarDarkFont(true)
            .navigationBarColorTransform(android.R.color.white)
            .navigationBarAlpha(0f)
            .fullScreen(true)
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

    /**
     * 初始化Activity进场动画
     */
    private fun initTransitionMode() {
        when (getOverridePendingTransitionMode()) {
            TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.left_out)
            TransitionMode.RIGHT -> overridePendingTransition(R.anim.right_in, R.anim.right_out)
            TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.top_out)
            TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
            TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
            TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            else -> {

            }
        }
    }

    override fun finish() {
        super.finish()
        when (getOverridePendingTransitionMode()) {
            TransitionMode.LEFT -> overridePendingTransition(R.anim.right_in, R.anim.right_out)
            TransitionMode.RIGHT -> overridePendingTransition(R.anim.left_in, R.anim.left_out)
            TransitionMode.TOP -> overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
            TransitionMode.BOTTOM -> overridePendingTransition(R.anim.top_in, R.anim.top_out)
            TransitionMode.SCALE -> overridePendingTransition(
                R.anim.scale_in_disappear,
                R.anim.scale_out_disappear
            )
            TransitionMode.FADE -> overridePendingTransition(
                R.anim.fade_in_disappear,
                R.anim.fade_out_disappear
            )
            else -> {

            }
        }
    }


    protected open fun showDialog(waitMsg: String = getString(R.string.waiting)) {
        WaitDialog.show(waitMsg).onBackPressedListener =
            OnBackPressedListener {
                //return true自动关闭等待/提示对话框
                true
            }
        //自定义布局
//        WaitDialog.show("Please Wait!")
//            .setCustomView(object : OnBindView<WaitDialog?>(R.layout.layout_custom_view) {
//                override fun onBind(dialog: WaitDialog?, v: View) {}
//            })

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

    protected open fun getOverridePendingTransitionMode(): TransitionMode {
        return TransitionMode.DEFAULT
    }


    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
        AppManager.getInstance().hideSoftKeyBoard(this)
        AppManager.getInstance().removeActivity(this);
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