package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        String movieItem = intent.getStringExtra("movieItem");
        MovieEntity movieEntity = JSON.parseObject(movieItem, MovieEntity.class);

        View cv = getWindow().getDecorView();
        RoundedImageView imageView = cv.findViewById(R.id.movie_detail_img);

        Glide.with(imageView)
                .load(Api.HOST + movieEntity.getLocalImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);

        TextView movieName = cv.findViewById(R.id.movie_detail_name);
        movieName.setText(movieEntity.getMovieName());

        TextView movieStar = cv.findViewById(R.id.movie_detail_star);
        movieStar.setText(movieEntity.getStar());
    }
}
