package com.example.webview2021;

import android.content.ComponentName;
import android.content.Intent;

import com.example.base.BaseApplication;
import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
import com.example.webview.command.ICommand;
import com.google.auto.service.AutoService;

import java.util.Map;

@AutoService({ICommand.class})
public class CommandOpenPage implements ICommand {

    @Override
    public String name() {
        return "openPage";
    }

    @Override
    public void execute(Map parameters, IMainProcess2WebViewProcessAidlInterface callback) {
        String targetClass = (String) parameters.get("target_class");
        if (!targetClass.isEmpty()) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.sApplication, targetClass));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.sApplication.startActivity(intent);
        }
    }
}
