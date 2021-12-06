package com.player.movie.http;

import com.player.movie.activity.MainActivity;
import com.player.movie.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

    public OkHttpClient.Builder getClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(chain -> {
            Request build = chain.request().newBuilder()
                    .addHeader("Authorization", (String) SharedPreferencesUtils.getParam("token",""))
                    .addHeader("Content-type","application/json;charset=UTF-8")
                    .build();
            return chain.proceed(build);
        });
        return builder;
    }
}
