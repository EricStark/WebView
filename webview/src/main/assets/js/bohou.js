var bohou = {};
bohou.os = {};
bohou.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
bohou.os.isAndroid = !bohou.os.isIOS;
bohou.callbacks = {}
// JS 提供的函数 takeNativeAction()
bohou.takeNativeAction = function(commandname, parameters) {
    console.log("js takeNativeAction() is invoked.")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    // java 中的代码中注入的方法
    // @JavascriptInterface
    // public void takeNativeAction(final String jsParams) {
    //
    // }
    if(window.bohou.os.isAndroid){
        console.log("android takeNativeAction() is invoked, request is : " + JSON.stringify(request));
        window.bohouwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.bohouwebview.postMessage(JSON.stringify(request))
    }
}

bohou.takeNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    bohou.callbacks[callbackname] = {callback:callback};
    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.bohou.os.isAndroid) {
        window.bohouwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.bohouwebview.postMessage(JSON.stringify(request))
    }
}
bohou.callback = function (callbackname, response) {
   var callbackobject = bohou.callbacks[callbackname];
   console.log("xxxx"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("xxxxxx"+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete bohou.callbacks[callbackname];
       }
   }
}

window.bohou = bohou;
