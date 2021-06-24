package com.yuaihen.wcdxg.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.ImmersionBar
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBackPressedListener
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.ToastUtil

/**
 * Created by Yuaihen.
 * on 2021/3/25
 */
abstract class BaseFragment : Fragment() {

    private lateinit var mContext: FragmentActivity
    private var mRootView: View? = null
    private var firstInit = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FragmentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //使用Navigation时避免重新创建Fragment
        val resId = getLayoutId()
        return if (resId != 0) {
            inflater.inflate(resId, container, false)
        } else {
            getBindingView(inflater, container)!!
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.d("hello", "onActivityCreated: ")
        initImmersionBar()
        initListener()
        initData()
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }


    open fun getLayoutId(): Int {
        return 0
    }

    open fun initListener() {
    }

    open fun initData() {

    }

    /**
     * 懒加载数据
     */
    open fun lazyLoadData() {

    }

    abstract fun unBindView()

    protected open fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d("hello", "onDestroyView")
        hideLoading()
        unBindView()
    }

    open fun showLoading(waitMsg: String = getString(R.string.waiting)) {
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

    open fun hideLoading() {
        WaitDialog.dismiss()
    }

    open fun logD(tag: String? = javaClass.simpleName, msg: String) {
        LogUtil.d(tag, msg)
    }

    protected open fun toast(msg: String) {
        mContext.runOnUiThread { ToastUtil.show(msg) }
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("hello", "onResume")
        if (firstInit) {
            firstInit = !firstInit
            lazyLoadData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("hello", "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.d("hello", "onDetach: ")
    }
}