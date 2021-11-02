package com.example.webview.webviewprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.base.BaseApplication;
import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
import com.example.webview.IWebViewProcess2MainProcessAidlInterface;
import com.example.webview.mainprocess.MainProcessCommandService;

public class WebViewProcessCommandDispatcher implements ServiceConnection {

    private static volatile WebViewProcessCommandDispatcher sInstance;
    private IWebViewProcess2MainProcessAidlInterface iWebViewProcessToMainProcessInterface;


    public static WebViewProcessCommandDispatcher getInstance() {
        if (sInstance == null) {
            synchronized (WebViewProcessCommandDispatcher.class) {
                sInstance = new WebViewProcessCommandDispatcher();
            }
        }
        return sInstance;
    }

    /**
     * 绑定服务
     */
    public void initAidlConnection() {
        Intent intent = new Intent(BaseApplication.sApplication, MainProcessCommandService.class);
        BaseApplication.sApplication.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        iWebViewProcessToMainProcessInterface = IWebViewProcess2MainProcessAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        iWebViewProcessToMainProcessInterface = null;
        //重新连接
        initAidlConnection();
    }

    @Override
    public void onBindingDied(ComponentName name) {
        iWebViewProcessToMainProcessInterface = null;
        //重新连接
        initAidlConnection();
    }

    public void executeCommand(String commandName, String params, BaseWebView baseWebView) {
        if (iWebViewProcessToMainProcessInterface != null) {
            //如果连接已经建立
            try {
                //执行 MainProcessCommandsManager.handleWebCommand()
                iWebViewProcessToMainProcessInterface.handleWebCommand(commandName, params, new IMainProcess2WebViewProcessAidlInterface.Stub() {
                    @Override
                    public void onResult(String callbackName, String response) throws RemoteException {
                        baseWebView.handleCallback(callbackName, response);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
