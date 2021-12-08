package com.player.movie.http;

import com.player.movie.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {
    @GET(Api.GETUSERDATA)
    Call<ResultEntity> getUserData();

    @GET(Api.GETCATEGORYLIST)
    Call<ResultEntity> getCategoryList(@Query("category")String category,@Query("classify")String classify);

    @GET(Api.GETALLCATEGORYLISTBYPAGENAME)
    Call<ResultEntity> getAllCategoryListByPageName(@Query("pageName")String pageName);
}
