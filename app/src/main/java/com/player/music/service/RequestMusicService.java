package com.player.music.service;
import com.player.music.api.Api;
import com.player.http.ResultEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RequestMusicService {
    @GET(Api.GETKEYWORDMUSIC)
    Call<ResultEntity> getKeywordMusic();

    @GET(Api.GETMUSICCLASSIFY)
    Call<ResultEntity> getMusicClassify();

    @GET(Api.GETMUSICLISTBYCLASSIFYID)
    Call<ResultEntity> getMusicListByClassifyId();

    @GET(Api.GETSINGERLIST)
    Call<ResultEntity> getSingerList();

    @GET(Api.GETCIRCLELISTBYTYPE)
    Call<ResultEntity> getCircleListByType();

    @GET(Api.GETMUSICPLAYMENU)
    Call<ResultEntity> getMusicPlayMenu();

    @POST(Api.GETMYSINGER)
    Call<ResultEntity> getMySinger();

    @GET(Api.GETMUSICRECORD)
    Call<ResultEntity> getMusicRecord();

    @POST(Api.INSERTMUSICRECORD)
    Call<ResultEntity> insertMusicRecord();

    @GET(Api.INSERTMUSICFAVORITE)
    Call<ResultEntity> insertMusicFavorite();

    @GET(Api.DELETEMUSICFAVORITE)
    Call<ResultEntity> deleteMusicFavorite();

    @GET(Api.QUERYMUSICFAVORITE)
    Call<ResultEntity> queryMusicFavorite();

    @GET(Api.SEARCHMUSIC)
    Call<ResultEntity> searchMusic();

    @GET(Api.GETSINGERCATEGORY)
    Call<ResultEntity> getSingerCategory();

    @GET(Api.SAVELIKE)
    Call<ResultEntity> saveLike();

    // 搜索
    @GET(Api.DELETELIKE)
    Call<ResultEntity> deleteLike();

    @PUT(Api.INSERTCOMMENT)
    Call<ResultEntity> insertComment();
}
