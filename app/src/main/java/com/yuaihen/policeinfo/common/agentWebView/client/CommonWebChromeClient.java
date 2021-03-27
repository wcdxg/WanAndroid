package com.yuaihen.policeinfo.common.agentWebView.client;

import android.util.Log;
import android.webkit.WebView;

import com.just.agentweb.WebChromeClient;

/**
 * Created by Yuaihen.
 * on 2020/11/12
 */
public class CommonWebChromeClient extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.i("CommonWebChromeClient", "onProgressChanged:" + newProgress + "  view:" + view);
    }
}
