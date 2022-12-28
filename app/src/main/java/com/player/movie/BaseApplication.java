package com.player.movie;

import android.app.Application;

import com.player.movie.entity.UserEntity;
import com.player.movie.utils.SharedPreferencesUtils;

public class BaseApplication extends Application {

    private static BaseApplication mApp;

    public static BaseApplication getInstance(){
        return mApp;
    }

    private String token;

    private UserEntity userEntity;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        token = (String) SharedPreferencesUtils.getParam(this,"token","");
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return  token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
