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

    //Fragment对用户可见的标记
    private var isUIVisible = false

    //Fragment的View加载完毕的标记
    private var isViewCreated = false

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
        if (mRootView == null) {
            val resId = getLayoutId()
            mRootView = if (resId != 0) {
                inflater.inflate(resId, container, false)
            } else {
                getBindingView(inflater, container)
            }
        }
        return mRootView!!
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initImmersionBar()
//            isViewCreated = true
        initListener()
        initData()
//            lazyLoad()
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
//        if (isVisibleToUser) {
//            isUIVisible = true
//            lazyLoad()
//        } else {
//            isUIVisible = false
//        }
//        onFragmentVisibleChange(isVisibleToUser)
//    }

    /**
     * Fragment 是否可见
     *
     * @param isUIVisible
     */
//    open fun onFragmentVisibleChange(isUIVisible: Boolean) {
//
//    }

//    open fun lazyLoad() {
//        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
//        if (isViewCreated && isUIVisible) {
//            loadData()
//            //数据加载完毕,恢复标记,防止重复加载
//            isViewCreated = false
//            isUIVisible = false
//        }
//    }

    open fun getLayoutId(): Int {
        return 0
    }

    open fun initListener() {
    }

    open fun initData() {
    }

//    open fun loadData() {
//    }

    abstract fun unBindView()

    protected open fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //页面销毁,恢复标记
//        isViewCreated = false
//        isUIVisible = false
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

}