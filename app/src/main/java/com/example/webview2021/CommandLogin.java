package com.example.webview2021;

import android.os.RemoteException;
import android.util.Log;

import com.example.base.autoservice.ServiceLoaderUtil;
import com.example.common.autoservice.IUserCenterService;
import com.example.common.eventbus.LoginEvent;
import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
import com.example.webview.command.ICommand;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@AutoService({ICommand.class})
public class CommandLogin implements ICommand {

    private IUserCenterService userCenterService = ServiceLoaderUtil.load(IUserCenterService.class);

    private static final String TAG = "CommandLogin";

    private IMainProcess2WebViewProcessAidlInterface callback;

    private String callbackName;

    public CommandLogin() {
        EventBus.getDefault().register(this);
    }

    @Override
    public String name() {
        return "login";
    }

    @Override
    public void execute(Map parameters, IMainProcess2WebViewProcessAidlInterface callback) {
        if (userCenterService != null) {
            if (!userCenterService.isLogined()) {
                userCenterService.login();
                this.callback = callback;
                this.callbackName = (String) parameters.get("callbackname");
            }
        }
    }

    @Subscribe
    public void onMessageEvent(LoginEvent loginEvent) {
        Log.e(TAG, "userName = " + loginEvent.name);
        HashMap<String, String> map = new HashMap<>();
        map.put("accountName", loginEvent.name);
        if (callback != null) {
            try {
                callback.onResult(callbackName, new Gson().toJson(map));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
