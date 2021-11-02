package com.example.webview.command;

import com.example.webview.IMainProcess2WebViewProcessAidlInterface;

import java.util.Map;

public interface ICommand {

    String name();

    void execute(Map parameters, IMainProcess2WebViewProcessAidlInterface callback);
}
