package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.webview.databinding.ActivityWebViewBinding;
import com.example.webview.utils.Const;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        //获取参数 defaultTitle isShow url
        String defaultTitle = getIntent().getExtras().getString(Const.TITLE);
        boolean isShow = getIntent().getExtras().getBoolean(Const.IS_SHOW_ACTION_BAR);
        String url = getIntent().getExtras().getString(Const.URL);
        //显示actionbar
        mBinding.actionBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        //显示默认title
        mBinding.title.setText(defaultTitle);
        //给back设置监听
        mBinding.back.setOnClickListener(v -> WebViewActivity.this.finish());
        //设置fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        WebViewFragment webViewFragment = WebViewFragment.newInstance(url, true);
        transaction.replace(R.id.web_view_fragment, webViewFragment);
        transaction.commit();
    }

    /**
     * 更新title
     *
     * @param title
     */
    public void updateTitle(String title) {
        mBinding.title.setText(title);
    }
}