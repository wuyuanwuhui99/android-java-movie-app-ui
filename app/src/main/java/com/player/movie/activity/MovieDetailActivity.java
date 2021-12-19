package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.adapter.StarRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.MovieStarEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.utils.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    MovieEntity movieEntity;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initData();
        getStarList();
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化详情页电影数据
     * @date: 2021-12-04 15:59
     */
    private void initData(){
        Intent intent = getIntent();
        String movieItem = intent.getStringExtra("movieItem");
        movieEntity = JSON.parseObject(movieItem, MovieEntity.class);

        view = getWindow().getDecorView();
        RoundedImageView imageView = view.findViewById(R.id.movie_detail_img);

        Glide.with(imageView)
                .load(Api.HOST + movieEntity.getLocalImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);

        //设置电影名称
        TextView movieName = view.findViewById(R.id.movie_detail_name);
        movieName.setText(movieEntity.getMovieName());

        //主演
        TextView movieStar = view.findViewById(R.id.movie_detail_star);
        if(movieEntity.getStar() != null){
            movieStar.setText("主演：" + movieEntity.getStar());
        }else{
            movieStar.setText("主演：暂无");
        }

        //设置电影简述
        TextView descriptionText = view.findViewById(R.id.description);
        if(movieEntity.getDescription() != null){
            descriptionText.setText(movieEntity.getDescription().trim());
        }else{
            descriptionText.setVisibility(View.GONE);
        }

        //计算得分和星星
        LinearLayout scoreLayout = view.findViewById(R.id.score_layout);
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
            TextView scoreText = view.findViewById(R.id.score);
            scoreText.setText(score.toString());
        }else{
            view.findViewById(R.id.no_score).setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.GONE);
        }

        TextView plotText = view.findViewById(R.id.plot);
        if(movieEntity.getPlot() != null){
            plotText.setText("\u3000\u3000" + movieEntity.getPlot());
        }else{
            plotText.setText("\u3000\u3000暂无");
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取主演列表
     * @date: 2021-12-11 12:11
     */
    private void getStarList(){
        Call<ResultEntity> userData = RequestUtils.getInstance().getStarList(movieEntity.getMovieId().toString());
        userData.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieStarEntity> movieStarList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieStarEntity.class);
                StarRecyclerViewAdapter starRecyclerViewAdapter = new StarRecyclerViewAdapter(movieStarList);
                LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.star_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(starRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }
}
