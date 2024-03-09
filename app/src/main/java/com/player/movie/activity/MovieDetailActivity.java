package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.adapter.StarRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.MovieStarEntity;
import com.player.movie.fragment.LikeMovieFragment;
import com.player.movie.fragment.RecommendMovieFragment;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    MovieEntity movieEntity;
    String movieItemString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initData();
        addFraction();
        setModuleTitle();
        getStarList();
        saveViewRecord();
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化详情页电影数据
     * @date: 2021-12-04 15:59
     */
    private void initData(){
        Intent intent = getIntent();
        movieItemString = intent.getStringExtra("movieItem");
        movieEntity = JSON.parseObject(movieItemString, MovieEntity.class);

        RoundedImageView imageView = findViewById(R.id.detail_movie_img);

        Glide.with(imageView)
                .load(Api.HOST + movieEntity.getLocalImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);

        //设置电影名称
        TextView movieName = findViewById(R.id.detail_movie_name);
        movieName.setText(movieEntity.getMovieName());

        //主演
        TextView movieStar = findViewById(R.id.detail_movie_star);
        if(movieEntity.getStar() != null){
            movieStar.setText("主演：" + movieEntity.getStar());
        }else{
            movieStar.setText("主演：暂无");
        }

        //设置电影简述
        TextView descriptionText = findViewById(R.id.detail_description);
        if(movieEntity.getDescription() != null){
            descriptionText.setText(movieEntity.getDescription().trim());
        }else{
            descriptionText.setVisibility(View.GONE);
        }

        //计算得分和星星
        LinearLayout scoreLayout = findViewById(R.id.detail_score_layout);
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
            TextView scoreText = findViewById(R.id.detail_score);
            scoreText.setText(String.valueOf(score));
        }else{
            findViewById(R.id.detail_no_score).setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.GONE);
        }

        TextView plotText = findViewById(R.id.detail_plot);
        if(movieEntity.getPlot() != null){
            plotText.setText("\u3000\u3000" + movieEntity.getPlot());
        }else{
            plotText.setText("\u3000\u3000暂无");
        }

        //如果movieId不存在，这不能播放电影
        if(movieEntity.getMovieId() == null){
            findViewById(R.id.detail_movie_play).setVisibility(View.GONE);
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 添加推荐和猜你想看模块
     * @date: 2022-08-18 22:05
     */
    private void addFraction(){
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_your_like_layout, new LikeMovieFragment(movieEntity))
                .replace(R.id.detail_recommend_layout, new RecommendMovieFragment(movieEntity,""))
                .commit();
    }

    /**
     * @author: wuwenqiang
     * @description: 设置每个模块的标题
     * @date: 2022-08-14 11:06
     */
    private void setModuleTitle(){
        TextView plotTitle = findViewById(R.id.detail_plot_title).findViewById(R.id.module_title);
        plotTitle.setText(R.string.detail_plot);

        TextView starText = findViewById(R.id.detail_star_title).findViewById(R.id.module_title);
        starText.setText(R.string.detail_star);
    }

    /**
     * @author: wuwenqiang
     * @description: 获取主演列表
     * @date: 2021-12-11 12:11
     */
    private void getStarList(){
        if(movieEntity.getMovieId() == null){
            findViewById(R.id.detail_star_layout).setVisibility(View.GONE);
            return;
        }
        Call<ResultEntity> call = RequestUtils.getInstance().getStarList(movieEntity.getMovieId().toString());
        call.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieStarEntity> movieStarList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieStarEntity.class);
                StarRecyclerViewAdapter starRecyclerViewAdapter = new StarRecyclerViewAdapter(movieStarList,MovieDetailActivity.this);
                LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailActivity.this);  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = findViewById(R.id.detail_star_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(starRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("获取获取主演列表失败");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 点击图片跳转到播放页
     * @date: 2021-12-22 23:13
     */
    public void goPlay(View v){
        if(movieEntity.getMovieId() != null){
            Intent intent = new Intent(this, MoviePlayActivity.class);
            intent.putExtra("movieItem",movieItemString);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), R.string.detail_no_play, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 插入浏览记录
     * @date: 2021-12-22 23:13
     */
    public void saveViewRecord(){
        Call<ResultEntity> call = RequestUtils.getInstance().saveViewRecord(movieEntity);
        call.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
