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
                .setPermissionInterceptor(getPermissionInterceptor())   //????????????
                .setWebLayout(getWebLayout())
                .setAgentWebUIController(getAgentWebUIController()) //?????????UI  AgentWeb3.0.0 ?????????
                .interceptUnkownUrl()   //??????????????????????????????Url AgentWeb 3.0.0 ?????????
                .setOpenOtherPageWays(getOpenOtherAppWay())
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())   //??????WebChromeClient????????????????????????WebChromeClientF
                .setAgentWebWebSettings(getAgentWebSettings())      //WebView???????????????
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)//??????1?????????????????????????????????2??????????????????ID -1???????????????????????????????????? AgentWeb 3.0.0 ?????????
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)    //???????????? Android 4.2.2 ??????????????????????????? ?????????AgentWebView????????????
                .createAgentWeb()   //??????AgentWeb???
                .ready()    //?????? WebSettings???
                .go(getUrl());  //WebView?????????url???????????????????????????


        // AgentWeb ?????????WebView????????????????????? ????????????????????? AgentWeb ???????????? ??? ??????WebView?????????????????????
        //????????????????????????webview?????????
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        // ????????????????????????
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
//        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        //mAgentWeb.getWebCreator().getWebView()  ??????WebView .
//		mAgentWeb.getWebCreator().getWebView().setOnLongClickListener();

        //?????????????????????
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
     * ????????????????????????scheme???????????? scheme://host:port/path?query&query
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
     * ???????????????????????????????????????????????????????????? AgentWeb 3.0.0 ?????????
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
