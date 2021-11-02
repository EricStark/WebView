package com.example.webview.mainprocess;

import android.os.RemoteException;

import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
import com.example.webview.IWebViewProcess2MainProcessAidlInterface;
import com.example.webview.command.ICommand;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class MainProcessCommandsManager extends IWebViewProcess2MainProcessAidlInterface.Stub {

    private static volatile MainProcessCommandsManager sInstance;
    private Map<String, ICommand> mCommandMap = new HashMap<>();

    private MainProcessCommandsManager() {
        ServiceLoader<ICommand> serviceLoader = ServiceLoader.load(ICommand.class);
        for (ICommand command : serviceLoader) {
            if (!mCommandMap.containsKey(command.name())) {
                mCommandMap.put(command.name(), command);
            }
        }
    }

    public static MainProcessCommandsManager getInstance() {
        if (sInstance == null) {
            synchronized (MainProcessCommandsManager.class) {
                if (sInstance == null) {
                    sInstance = new MainProcessCommandsManager();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void handleWebCommand(String commandName, String jsonParams, IMainProcess2WebViewProcessAidlInterface callback) throws RemoteException {
        executeCommand(commandName, new Gson().fromJson(jsonParams, Map.class), callback);
    }

    public void executeCommand(String commandName, Map<String, String> params, IMainProcess2WebViewProcessAidlInterface callback) {
        mCommandMap.get(commandName).execute(params, callback);
    }
}
