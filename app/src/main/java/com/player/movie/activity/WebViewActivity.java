package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.player.movie.R;
import com.player.movie.myinterface.JavaScriptinterface;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initData();
    }

    private void initData(){
        WebView webView = (WebView) findViewById(R.id.movie_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.addJavascriptInterface(new JavaScriptinterface(this),"plus");
//        deleteDatabase("webview.db");
//        deleteDatabase("webviewCache.db");
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
        if (requestCode == 2) {
//            // 从相册返回的数据
//            Log.e(this.getClass().getName(), "Result:" + data.toString());
//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
//                iv_image.setImageURI(uri);
//                Log.e(this.getClass().getName(), "Uri:" + String.valueOf(uri));
//            }
        }
    }
}
