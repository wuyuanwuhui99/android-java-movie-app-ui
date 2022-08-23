package com.player.movie.http;

import com.player.movie.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestService {
    @GET(Api.GETUSERDATA)
    Call<ResultEntity> getUserData();

    @GET(Api.GETCATEGORYLIST)
    Call<ResultEntity> getCategoryList(@Query("category")String category,@Query("classify")String classify);

    @GET(Api.GETALLCATEGORYLISTBYPAGENAME)
    Call<ResultEntity> getAllCategoryListByPageName(@Query("pageName")String pageName);

    @GET(Api.GETKEYWORD)
    Call<ResultEntity> getKeyWord(@Query("classify")String classify);

    @GET(Api.GETUSERMSG)
    Call<ResultEntity> getUserMsg();

    @GET(Api.GETPLAYRECORD)
    Call<ResultEntity> getPlayRecord();

    @GET(Api.GETSTAR)
    Call<ResultEntity> getStarList(@Path("movieId") String movieId);

    @GET(Api.GETYOURLIKES)
    Call<ResultEntity> getYourLikes(@Query("labels") String labels,@Query("classify") String classify);

    @GET(Api.GETRECOMMEND)
    Call<ResultEntity> getRecommend(@Query("classify") String classify);

    @GET(Api.GETTOPMOVIELIST)
    Call<ResultEntity> getTopMovieList(@Query("classify") String classify,@Query("category") String category);

    @GET(Api.GETMOVIEURL)
    Call<ResultEntity> getMovieUrl(@Query("movieId") Long movieId);

    // 搜索
    @GET(Api.GETSEARCHRESULT)
    Call<ResultEntity> search(
            @Query("classify") String classify,
            @Query("category") String category,
            @Query("label") String label,
            @Query("star") String star,
            @Query("director") String director,
            @Query("keyword") String keyword,
            @Query("pageSize") int pageSize,
            @Query("pageNum") int pageNum

    );
}
