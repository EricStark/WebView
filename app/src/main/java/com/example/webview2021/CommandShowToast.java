package com.example.webview2021;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.base.BaseApplication;
import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
import com.example.webview.command.ICommand;
import com.google.auto.service.AutoService;

import java.util.Map;

@AutoService({ICommand.class})
public class CommandShowToast implements ICommand {

    @Override
    public String name() {
        return "showToast";
    }

    @Override
    public void execute(Map parameters, IMainProcess2WebViewProcessAidlInterface callback) {
        String commandParam = (String) parameters.get("message");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(BaseApplication.sApplication,
                commandParam, Toast.LENGTH_SHORT).show());
    }
}
