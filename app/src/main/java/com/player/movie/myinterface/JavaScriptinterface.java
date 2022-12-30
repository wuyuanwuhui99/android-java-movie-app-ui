package com.player.movie.myinterface;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.player.movie.utils.PlugCamera;

public class JavaScriptinterface {
    Context context;
    Activity activity;
    PlugCamera plugCamera;
    public JavaScriptinterface(Context context, Activity activity) {
        this.context= context;
        this.activity = activity;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void chooseImages() {
        plugCamera = new PlugCamera();
        plugCamera.showCamera(context,activity);
    }

    public int getType(){
        return plugCamera.getType();
    }
}