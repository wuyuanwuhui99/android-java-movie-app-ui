package com.player.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import com.player.R;
import com.player.common.Constants;
import com.player.movie.BaseApplication;
import com.player.movie.utils.SharedPreferencesUtils;


public class LaunchActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        init();
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化加载缓存token
     * @date: 2024-04-20 18:42
     */
    void init(){
        Handler handler = new Handler();
        Runnable runnable = () -> {
            String token = (String) SharedPreferencesUtils.getParam(LaunchActivity.this, Constants.TOKEN,"");
            Intent intent;
            if("".equals(token) && token != null){
                BaseApplication.getInstance().setToken(token);
                intent = new Intent(LaunchActivity.this,MainActivity.class);
            }else{
                intent = new Intent(LaunchActivity.this,LoginActivity.class);
            }
            startActivity(intent);
        };
        handler.postDelayed(runnable, 1000); // 延时1000毫秒执行
    }
}
