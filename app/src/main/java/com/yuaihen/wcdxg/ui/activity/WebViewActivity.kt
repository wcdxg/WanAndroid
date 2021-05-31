package com.yuaihen.wcdxg.ui.activity

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.gyf.immersionbar.ImmersionBar
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.common.agentWebView.base.BaseAgentWebActivity
import com.yuaihen.wcdxg.databinding.ActivityWebviewBinding
import com.yuaihen.wcdxg.ui.interf.OnTitleViewBackClickListener
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.visible

/**
 * Created by Yuaihen.
 * on 2020/11/10
 * WebView加载
 */
class WebViewActivity : BaseAgentWebActivity(), OnTitleViewBackClickListener {

    private val TAG = "WebViewActivity"

    private lateinit var binding: ActivityWebviewBinding

    override fun getBindingView(): View {
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.titleView.setOnTitleViewBackClickListener(this)
    }

    override fun initData() {

    }

    override fun initAgentWebEnd() {
        //注入Js 原生回调
//        mAgentWeb?.jsInterfaceHolder?.addJavaObject(
//            "android",
//            AndroidInterface(mAgentWeb, this, object : JSInterfaceListener {
//                //H5 明星报告结果页重新拍照
//                override fun retakeForScanFace() {
//                    start2Activity(ScanFaceActivity::class.java, null, true)
//                }
//            })
//        )
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }

    //不支持ConstraintLayout
    override fun getAgentWebParent(): ViewGroup {
        return binding.container
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 进度条颜色
     */
    override fun getIndicatorColor(): Int {
        return resources.getColor(R.color.bili_bili_pink)
    }

    /**
     * 进度条高度
     */
    override fun getIndicatorHeight(): Int {
        return 0
    }


    override fun getUrl(): String {
        val url = intent.getStringExtra(Constants.URL) ?: ""
        LogUtil.d("WebViewActivity", "getUrl: $url")
        return url
    }

    override fun onClickBack() {
        // true表示AgentWeb处理了该事件
        if (!mAgentWeb.back()) {
            finish()
        }
    }

    override fun getWebChromeClient(): WebChromeClient? {
        return object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                val url = view?.url ?: ""
                LogUtil.d(TAG, "onReceivedTitle: $title $url")
                binding.titleView.setTitle(title ?: "")
                binding.titleView.visible()
            }

            override fun onCreateWindow(
                view: WebView,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message
            ): Boolean {
                Log.d(TAG, "onCreateWindow: ")
                val newWebView = WebView(view.context)
                newWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        //在此处进行跳转URL的处理, 一般情况下_black需要重新打开一个页面,
                        val url = request.url.toString()
                        Log.d(TAG, "onCreateWindow request: $url ")
                        val data = Bundle().apply {
                            putString(Constants.URL, url)
                        }
                        start2Activity(WebViewActivity::class.java, data, false)
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }

                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }


}