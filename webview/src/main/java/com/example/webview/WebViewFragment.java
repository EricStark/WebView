package com.example.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.loadsir.ErrorCallback;
import com.example.base.loadsir.LoadingCallback;
import com.example.webview.databinding.FragmentWebviewBinding;
import com.example.webview.webviewprocess.BaseWebView;
import com.example.webview.webviewprocess.settings.WebViewDefaultSettings;
import com.example.webview.utils.Const;
import com.example.webview.webviewprocess.webchromeclient.TWebChromeClient;
import com.example.webview.webviewprocess.webviewclient.TWebViewClient;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class WebViewFragment extends Fragment implements WebViewCallBack, OnRefreshListener {

    private String mUrl;
    private FragmentWebviewBinding mBinding;
    private LoadService mLoadService;
    private boolean mCanNativeRefresh;
    private boolean mIsFinishError = false;
    private static final String TAG = "WebViewFragment";

    public static WebViewFragment newInstance(String url, boolean canNativeRefresh) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.URL, url);
        bundle.putBoolean(Const.CAN_NATIVE_REFRESH, canNativeRefresh);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false);
        /**
         * 这步移动到 Settings 中完成
         *
         * mBinding.webview.getSettings().setJavaScriptEnabled(true);
         */
        WebViewDefaultSettings.getInstance().setSettings(mBinding.webview);

        mBinding.webview.loadUrl(mUrl);

        //给 smart_refresh_layout 注册 OnReloadListener
        mLoadService = LoadSir.getDefault()
                .register(mBinding.smartrefreshlayout, (Callback.OnReloadListener) v -> {
                    mLoadService.showCallback(LoadingCallback.class);
                    mBinding.webview.reload();
                });
        /**
         * 这部分移动到 BaseWebView 完成
         *
         * mBinding.webview.setWebViewClient(new TWebViewClient(this));
         * mBinding.webview.setWebChromeClient(new TWebChromeClient(this));
         */
        //调用 BaseWebView 中的方法 注册 CallBack
        ((BaseWebView) mBinding.webview).registerWebViewCallBack(this);

        //设置 smart_refresh_layout 刷新监听
        mBinding.smartrefreshlayout.setOnRefreshListener(this);

        //设置 smart_refresh_layout 是否支持下拉刷新
        mBinding.smartrefreshlayout.setEnableRefresh(mCanNativeRefresh);

        //设置 smart_refresh_layout 不要刷新更多
        mBinding.smartrefreshlayout.setEnableLoadMore(false);

        // return mBinding.getRoot();
        // 注意返回值
        return mLoadService.getLoadLayout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(Const.URL);
            mCanNativeRefresh = bundle.getBoolean(Const.CAN_NATIVE_REFRESH);
        }
    }

    /**
     * WebView页面开始加载回调
     * 显示Loading页面
     *
     * @param url
     */
    @Override
    public void pageStarted(String url) {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    /**
     * WenView页面加载完成回调 发生错误和正常都会回调此方法
     * 如果发生错误-->设置SmartRefreshLayout重新刷新
     * 如果正常完成-->根据用户设置SmartRefreshLayout是否支持刷新
     *
     * @param url
     */
    @Override
    public void pageFinished(String url) {
        if (mIsFinishError) {
            mBinding.smartrefreshlayout.setEnableRefresh(true);
        } else {
            mBinding.smartrefreshlayout.setEnableRefresh(mCanNativeRefresh);
        }
        Log.e(TAG, "pageFinished");
        mBinding.smartrefreshlayout.finishRefresh();
        if (mLoadService != null) {
            if (mIsFinishError) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                mLoadService.showSuccess();
            }
        }
        mIsFinishError = false;
    }

    /**
     * WebView加载发生错误-->结束SmartRefreshLayout刷新并且显示错误页面
     */
    @Override
    public void onError() {
        Log.e(TAG, "onError");
        mIsFinishError = true;
        mBinding.smartrefreshlayout.finishRefresh();
    }

    /**
     * 获取到了网页标题
     *
     * @param title
     */
    @Override
    public void updateTitle(String title) {
        if (title != null && getActivity() instanceof WebViewActivity) {
            ((WebViewActivity) getActivity()).updateTitle(title);
        }
    }

    /**
     * SmartRefreshLayout的刷新监听回调
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //如果刷新 WebView 则重新加载
        mBinding.webview.reload();
    }
}
