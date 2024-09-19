package com.player.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.player.R;
import com.player.common.Constants;
import com.player.http.RequestUtils;
import com.player.http.ResultEntity;
import com.player.movie.BaseApplication;
import com.player.movie.entity.UserEntity;
import com.player.movie.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            if(!"".equals(token) && token != null){
                getUserData();
            }else{
                startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
            }
        };
        handler.postDelayed(runnable, 1000); // 延时1000毫秒执行
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2021-12-04 15:59
     */
    private void getUserData(){
        Call<ResultEntity> userData = RequestUtils.getMovieInstance().getUserData();
        userData.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                Gson gson = new Gson();
                ResultEntity body = response.body();
                BaseApplication instance = BaseApplication.getInstance();
                Intent intent;
                if(body.getToken() != null){
                    instance.setToken(body.getToken());
                    instance.setUserEntity(gson.fromJson(gson.toJson(body.getData()), UserEntity.class));
                    SharedPreferencesUtils.setParam(LaunchActivity.this, Constants.TOKEN,response.body().getToken());
                    intent = new Intent(LaunchActivity.this,MainActivity.class);
                }else{
                    intent = new Intent(LaunchActivity.this,LoginActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 前面所有页面置空
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                Intent intent = new Intent(LaunchActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 前面所有页面置空
                startActivity(intent);
                finish();
            }
        });
    }
}
