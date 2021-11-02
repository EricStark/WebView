package com.example.webview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class MainProcessCommandService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return MainProcessCommandsManager.getInstance();
    }
}
