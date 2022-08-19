package com.player.movie;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.player.movie.database.SearchWordDatabase;
import com.player.movie.state.State;
import com.player.movie.utils.SharedPreferencesUtils;

public class BaseApplication extends Application {

    private static BaseApplication mApp;

    public static BaseApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        State.token = (String) SharedPreferencesUtils.getParam(this,"token","");
    }
}
