package com.player.movie.myinterface;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.player.movie.BaseApplication;
import com.player.movie.dialog.PlugCamera;

public class JavaScriptinterface {
    private Context context;
    private Activity activity;
    private PlugCamera plugCamera;
    private String data;
    public JavaScriptinterface(Context context, Activity activity) {
        this.context= context;
        this.activity = activity;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void chooseImages() {
        plugCamera = new PlugCamera(context,activity);
        plugCamera.showCamera();
    }

    public String getCheck(){
        return plugCamera.getCheck();
    }

    /**
     * 获取token方法，与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public String getToken(){
        return JSON.toJSONString(BaseApplication.getInstance().getToken());
    }

    /**
     * 获取用户信息，与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public String getUserData(){
        return JSON.toJSONString(BaseApplication.getInstance().getUserEntity());
    }
}