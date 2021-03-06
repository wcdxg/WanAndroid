package com.yuaihen.wcdxg.common.agentWebView.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.yuaihen.wcdxg.R;
import com.yuaihen.wcdxg.common.agentWebView.FragmentKeyDown;
import com.yuaihen.wcdxg.common.agentWebView.client.MiddlewareWebViewClient;

/**
 * Created by cenxiaozhong on 2017/7/22.
 * source code  https://github.com/Justson/AgentWeb
 */
public abstract class BaseAgentWebFragment extends Fragment implements FragmentKeyDown {
    private static final String TAG = "AgentWebFragment";
    protected AgentWeb mAgentWeb;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebViewClient mMiddleWareWebClient;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private AgentWebUIControllerImplBase mAgentWebUIController;
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBundle();
        initListener();

        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .closeIndicator()
//                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())     //?????????????????????????????????-1????????????????????????2????????????dp???
                .setWebView(getWebView())
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(getWebChromeClient())
                .setAgentWebWebSettings(getAgentWebSettings())  //WebView???????????????
                .setWebLayout(getWebLayout())
                .setPermissionInterceptor(getPermissionInterceptor())
                .interceptUnkownUrl()           //??????????????????????????????Url AgentWeb 3.0.0 ?????????
                .setOpenOtherPageWays(getOpenOtherAppWay())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setAgentWebUIController(getAgentWebUIController())
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)
//                .useMiddlewareWebChrome(getMiddleWareWebChrome())
//                .useMiddlewareWebClient(getMiddleWareWebClient())
                .createAgentWeb()//
                .ready()//
                .go(getUrl());


        //????????????????????????webview?????????
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        // ????????????????????????
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
        //?????????????????????
        mAgentWeb.getAgentWebSettings().getWebSettings().setSupportMultipleWindows(true);

        //AgentWeb ?????????WebView????????????????????? ????????????????????? AgentWeb ???????????? ??? ??????WebView?????????????????????
        //mAgentWeb.getWebCreator().getWebView()  ??????WebView .
//        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_IF_CONTENT_SCROLLS);
        //mAgentWeb.getWebCreator().getWebView().setOnLongClickListener();


        initAgentWebEnd();
    }

    protected abstract int getLayoutId();

    protected abstract void initBundle();

    protected abstract void initListener();

    protected abstract void initAgentWebEnd();

//    protected abstract MiddlewareWebClientBase getMiddleWareWebClient();

    /**
     * ???????????????????????????????????????????????????????????? AgentWeb 3.0.0 ?????????
     * DefaultWebClient.OpenOtherPageWays.ASK
     */
    protected @Nullable
    DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
        return DefaultWebClient.OpenOtherPageWays.ASK;
    }


    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event);
    }

    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return mAgentWebUIController;
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
    public void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    protected abstract @Nullable
    String getUrl();

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    protected @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return null;
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

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

//    protected @NonNull
//    MiddlewareWebChromeBase getMiddleWareWebChrome() {
//        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                setTitle(view, title);
//            }
//        };
//    }

}
