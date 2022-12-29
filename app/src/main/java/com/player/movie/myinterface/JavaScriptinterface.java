package com.player.movie.myinterface;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptinterface {
    Context context;
    public JavaScriptinterface(Context c) {
        context= c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void chooseImages() {
        // 使用意图直接调用手机相册
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 打开手机相册,设置请求码
        context.startActivity(intent);
    }
}