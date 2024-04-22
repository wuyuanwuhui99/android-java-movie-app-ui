package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.player.R;
import com.player.movie.myinterface.JavaScriptinterface;
import com.player.movie.utils.CommonUtils;
import com.player.movie.dialog.PlugCamera;

import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    JavaScriptinterface javaScriptinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initData();
    }

    private void initData(){
        webView = (WebView) findViewById(R.id.movie_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.clearCache(true);
        javaScriptinterface = new JavaScriptinterface(this, WebViewActivity.this);
        webView.addJavascriptInterface(javaScriptinterface,"plus");
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PlugCamera.REQUEST_CODE_CAMERA) {
            String base64;
            if(javaScriptinterface.getType() == 1){
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                base64 = CommonUtils.bitmapToBase64(bitmap);
            }else {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    base64 = CommonUtils.bitmapToBase64(bitmap);
                }catch (IOException e){
                    base64 = "";
                }
            }
            webView.evaluateJavascript("javascript:plus.chooseImagesCallback('"+base64+"')", value -> {});
        }
    }
}
