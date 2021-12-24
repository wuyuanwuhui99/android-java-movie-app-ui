package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.entity.MovieEntity;

public class MoviePlayActivity extends AppCompatActivity {
    View view;
    MovieEntity movieEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化电影播放页数据
     * @date: 2021-12-04 15:59
     */
    private void initData(){
        Intent intent = getIntent();
        movieEntity = JSON.parseObject(intent.getStringExtra("movieItem"), MovieEntity.class);
        view = getWindow().getDecorView();
        //设置电影名称
        TextView movieName = view.findViewById(R.id.play_movie_name);
        movieName.setText(movieEntity.getMovieName());
        //设置主演
        TextView movieStar = view.findViewById(R.id.play_star);
        if(movieEntity.getStar() != null){
            movieStar.setText(movieEntity.getStar());
        }else{
            movieStar.setText("主演：暂无");
        }

        //计算得分和星星
        LinearLayout scoreLayout = view.findViewById(R.id.play_score_layout);
        Double score = movieEntity.getScore();
        if(score != null && score != 0){
            for(int i = 0; i < 5; i++){
                ImageView scoreImageView = (ImageView) scoreLayout.getChildAt(i);
                if(score >= (i+1)*2){
                    scoreImageView.setImageResource(R.mipmap.icon_full_star);
                }else if(score < (i+1)*2 && score >= i*2 + 1){
                    scoreImageView.setImageResource(R.mipmap.icon_half_star);
                }
            }
            TextView scoreText = view.findViewById(R.id.play_score);
            scoreText.setText(score.toString());
        }else{
            view.findViewById(R.id.play_no_score).setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.GONE);
        }
    }
}
