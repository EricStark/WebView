package com.example.webview2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.base.autoservice.ServiceLoaderUtil;
import com.example.common.autoservice.IWebViewService;
import com.example.webview.WebViewActivity;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_jump);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IWebViewService webViewService = ServiceLoaderUtil.load(IWebViewService.class);
        Log.e("bohou", "webViewService = " + webViewService);
        if (webViewService != null) {
//            webViewService.startWebViewActivity(this, "https://www.baidu.com/", "百度", true);
            webViewService.startDemoHtml(this);
        }
    }
}