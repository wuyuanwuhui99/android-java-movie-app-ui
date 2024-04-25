package com.player.http;

import com.player.movie.api.Api;
import com.player.movie.service.RequestMusicService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class  RequestUtils {

   static public RequestMusicService getMovieInstance(){
      return new Retrofit.Builder()
              .baseUrl(Api.HOST)
              .client(new TokenHeaderInterceptor().getClient().build())
              .addConverterFactory(GsonConverterFactory.create())
              .build().create(RequestMusicService.class);
   }

   static public com.player.music.service.RequestMusicService getMusicInstance(){
      return new Retrofit.Builder()
              .baseUrl(Api.HOST)
              .client(new TokenHeaderInterceptor().getClient().build())
              .addConverterFactory(GsonConverterFactory.create())
              .build().create(com.player.music.service.RequestMusicService.class);
   }
}
