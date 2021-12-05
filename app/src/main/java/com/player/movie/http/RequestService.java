package com.player.movie.http;

import com.player.movie.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RequestService {
    @Headers({"Content-type:application/json;charset=UTF-8;"})
    @GET(Api.GETUSERDATA)
    Call<ResultEntity> getUserData();
}
