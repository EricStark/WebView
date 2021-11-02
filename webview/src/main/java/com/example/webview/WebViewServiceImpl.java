package com.example.webview;

import android.content.Context;
import android.content.Intent;

import com.example.common.autoservice.IWebViewService;
import com.example.webview.utils.Const;
import com.google.auto.service.AutoService;

import androidx.fragment.app.Fragment;

@AutoService({IWebViewService.class})
public class WebViewServiceImpl implements IWebViewService {

    @Override
    public void startWebViewActivity(Context context, String url, String defaultTitle, boolean isShowActionBar) {
        if (context != null) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(Const.URL, url);
            intent.putExtra(Const.TITLE, defaultTitle);
            intent.putExtra(Const.IS_SHOW_ACTION_BAR, isShowActionBar);
            context.startActivity(intent);
        }
    }

    @Override
    public Fragment getWebViewFragment(String url, boolean canNativeRefresh) {
        return WebViewFragment.newInstance(url, canNativeRefresh);
    }

    @Override
    public void startDemoHtml(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Const.URL, Const.ANDROID_ASSET_URI+"demo.html");
        intent.putExtra(Const.TITLE, "本地测试网页");
        intent.putExtra(Const.IS_SHOW_ACTION_BAR, false);
        context.startActivity(intent);
    }
}
