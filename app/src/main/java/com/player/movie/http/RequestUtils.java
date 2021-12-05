package com.player.movie.http;

import com.player.movie.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class  RequestUtils {

   static public RequestService getInstance(){
      return new Retrofit.Builder()
              .baseUrl(Api.HOST)
              .client(new TokenHeaderInterceptor().getClient().build())
              .addConverterFactory(GsonConverterFactory.create())
              .build().create(RequestService.class);
   }

//   static public RequestService requestService = new Retrofit.Builder()
//           .baseUrl(Api.HOST)
//           .client(new TokenHeaderInterceptor().getClient().build())
//           .addConverterFactory(GsonConverterFactory.create())
//           .build().create(RequestService.class);
}
