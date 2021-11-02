// IMainProcess2WebViewProcessAidlInterface.aidl
package com.example.webview;

// Declare any non-default types here with import statements

interface IMainProcess2WebViewProcessAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onResult(String callbackName, String response);
}