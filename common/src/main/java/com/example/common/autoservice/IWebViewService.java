package com.example.common.autoservice;

import android.content.Context;

import androidx.fragment.app.Fragment;

/**
 * WebView 模块对 common的接口下沉
 */
public interface IWebViewService {

    void startWebViewActivity(Context context, String url, String defaultTitle, boolean isShowActionBar);

    Fragment getWebViewFragment(String url, boolean canNativeRefresh);

    void startDemoHtml(Context context);
}
