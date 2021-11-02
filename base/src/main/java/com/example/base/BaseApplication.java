package com.example.base;

import android.app.Application;

public class BaseApplication extends Application {

    public static BaseApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
