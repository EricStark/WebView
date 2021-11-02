package com.example.webview.webviewprocess.bean;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public class JsParam {

    /**
     * 命令的名字
     */
    public String name;
    /**
     * 命令的参数 是 json格式
     */
    public JsonObject param;
}
