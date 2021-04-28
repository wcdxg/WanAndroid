package com.yuaihen.wcdxg.common.agentWebView.base;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.yuaihen.wcdxg.R;
import com.yuaihen.wcdxg.base.BaseActivity;

/**
 * Created by cenxiaozhong on 2017/7/22.
 * <p>
 * source code  https://github.com/Justson/AgentWeb
 */

public abstract class BaseAgentWebActivity extends BaseActivity {

    protected AgentWeb mAgentWeb;
    private AgentWebUIControllerImplBase mAgentWebUIController;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        buildAgentWeb();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        buildAgentWeb();
    }


    protected void buildAgentWeb() {
        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setWebView(getWebView())
                .setPermissionInterceptor(getPermissionInterceptor())   //权限拦截
                .setWebLayout(getWebLayout())
                .setAgentWebUIController(getAgentWebUIController()) //自定义UI  AgentWeb3.0.0 加入。
                .interceptUnkownUrl()   //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(getOpenOtherAppWay())
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())   //设置WebChromeClient中间件，支持多个WebChromeClientF
                .setAgentWebWebSettings(getAgentWebSettings())      //WebView的一些设置
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)//参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)    //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .createAgentWeb()   //创建AgentWeb。
                .ready()    //设置 WebSettings。
                .go(getUrl());  //WebView载入该url地址的页面并显示。


        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        //将图片调整到适合webview的大小
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        // 缩放至屏幕的大小
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
//        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .
//		mAgentWeb.getWebCreator().getWebView().setOnLongClickListener();

        //支持新窗口打开
        mAgentWeb.getAgentWebSettings().getWebSettings().setSupportMultipleWindows(true);

        initAgentWebEnd();

    }

    protected void initAgentWebEnd() {

    }

    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    public AgentWeb getAgentWeb() {
        return this.mAgentWeb;
    }

    protected static class ErrorLayoutEntity {
        private int layoutRes = R.layout.agentweb_error_page;
        private int reloadId;

        public void setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            if (layoutRes <= 0) {
                layoutRes = -1;
            }
        }

        public void setReloadId(int reloadId) {
            this.reloadId = reloadId;
            if (reloadId <= 0) {
                reloadId = -1;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }


    /**
     * 页面空白，请检查scheme是否加上 scheme://host:port/path?query&query
     */
    protected abstract String getUrl();

    public @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return null;
    }

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    protected @Nullable
    WebViewClient getWebViewClient() {
        return null;
    }


    protected @Nullable
    WebView getWebView() {
        return null;
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    public @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return null;
    }

    /**
     * 打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
     * DefaultWebClient.OpenOtherPageWays.ASK
     */
    public @Nullable
    DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
        return null;
    }

    protected @NonNull
    MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(view, title);
            }
        };
    }

    protected void setTitle(WebView view, String title) {

    }

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
        };
    }
}
