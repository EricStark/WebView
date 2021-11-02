// IWebViewProcess2MainProcessAidlInterface.aidl
package com.example.webview;

import com.example.webview.IMainProcess2WebViewProcessAidlInterface;
// Declare any non-default types here with import statements

interface IWebViewProcess2MainProcessAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleWebCommand(String commandName, String jsonParams, in IMainProcess2WebViewProcessAidlInterface callback);
}