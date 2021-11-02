package com.example.webview.webviewprocess;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.webview.WebViewCallBack;
import com.example.webview.webviewprocess.bean.JsParam;
import com.example.webview.webviewprocess.settings.WebViewDefaultSettings;
import com.example.webview.webviewprocess.webchromeclient.TWebChromeClient;
import com.example.webview.webviewprocess.webviewclient.TWebViewClient;
import com.google.gson.Gson;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseWebView extends WebView {

    private static final String TAG = "BaseWebView";

    public BaseWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化的时候绑定服务
        WebViewProcessCommandDispatcher.getInstance().initAidlConnection();

        WebViewDefaultSettings.getInstance().setSettings(this);
        /**
         * 1)将这个对象 设置为 bohouwebview 给Js使用
         * 2)在 Js中 通过 window 获取 bohouwebview 对象
         */
        addJavascriptInterface(this, "bohouwebview");
    }

    public void registerWebViewCallBack(WebViewCallBack webViewCallBack) {
        setWebViewClient(new TWebViewClient(webViewCallBack));
        setWebChromeClient(new TWebChromeClient(webViewCallBack));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParams) {
        Log.e(TAG, "takeNativeAction() in android code " + jsParams);
        if (!jsParams.isEmpty()) {
            //将 jsParams 转化成 自定义的javabean类
            final JsParam jsParam = new Gson().fromJson(jsParams, JsParam.class);
            if (jsParam != null) {
                final String commandName = jsParam.name;
                final String command = new Gson().toJson(jsParam.param);
                WebViewProcessCommandDispatcher.getInstance().executeCommand(commandName, command, this);
            }
        }
    }

    public void handleCallback(String callbackName, String response) {
        if (callbackName != null && response != null) {
            String js = "javascript:bohou.callback('" + callbackName + "'," + response + ")";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                post(() -> evaluateJavascript(js, null));
            }
        }
    }
}
