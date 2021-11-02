package com.example.webview.webviewprocess.webchromeclient;


import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.webview.WebViewCallBack;

public class TWebChromeClient extends WebChromeClient {

    private WebViewCallBack mWebViewCallBack;
    private static final String TAG = "TWebChromeClient";

    public TWebChromeClient(WebViewCallBack callBack) {
        this.mWebViewCallBack = callBack;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mWebViewCallBack != null) {
            mWebViewCallBack.updateTitle(title);
        } else {
            Log.e(TAG, "mWebViewCallBack is null");
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG, consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
