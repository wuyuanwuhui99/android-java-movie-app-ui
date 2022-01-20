package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.adapter.StarRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.MovieStarEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    MovieEntity movieEntity;
    View view;
    String movieItemString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initData();
        getStarList();
        getYourLikes();
        getRecommend();
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

        view = getWindow().getDecorView();
        RoundedImageView imageView = view.findViewById(R.id.detail_movie_img);

        Glide.with(imageView)
                .load(Api.HOST + movieEntity.getLocalImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);

        //设置电影名称
        TextView movieName = view.findViewById(R.id.detail_movie_name);
        movieName.setText(movieEntity.getMovieName());

        //主演
        TextView movieStar = view.findViewById(R.id.detail_movie_star);
        if(movieEntity.getStar() != null){
            movieStar.setText("主演：" + movieEntity.getStar());
        }else{
            movieStar.setText("主演：暂无");
        }

        //设置电影简述
        TextView descriptionText = view.findViewById(R.id.detail_description);
        if(movieEntity.getDescription() != null){
            descriptionText.setText(movieEntity.getDescription().trim());
        }else{
            descriptionText.setVisibility(View.GONE);
        }

        //计算得分和星星
        LinearLayout scoreLayout = view.findViewById(R.id.detail_score_layout);
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
            TextView scoreText = view.findViewById(R.id.detail_score);
            scoreText.setText(score.toString());
        }else{
            view.findViewById(R.id.detail_no_score).setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.GONE);
        }

        TextView plotText = view.findViewById(R.id.detail_plot);
        if(movieEntity.getPlot() != null){
            plotText.setText("\u3000\u3000" + movieEntity.getPlot());
        }else{
            plotText.setText("\u3000\u3000暂无");
        }

        //如果movieId不存在，这不能播放电影
        if(movieEntity.getMovieId() == null){
            view.findViewById(R.id.detail_movie_play).setVisibility(View.GONE);
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取主演列表
     * @date: 2021-12-11 12:11
     */
    private void getStarList(){
        if(movieEntity.getMovieId() == null){
            view.findViewById(R.id.detail_star_layout).setVisibility(View.GONE);
            return;
        }
        Call<ResultEntity> call = RequestUtils.getInstance().getStarList(movieEntity.getMovieId().toString());
        call.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieStarEntity> movieStarList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieStarEntity.class);
                StarRecyclerViewAdapter starRecyclerViewAdapter = new StarRecyclerViewAdapter(movieStarList);
                LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailActivity.this);  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.detail_star_recycler_view);
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
     * @description: 获取猜你想看
     * @date: 2021-12-11 12:11
     */
    private void getYourLikes(){
        String label = movieEntity.getLabel() == null ? movieEntity.getType() : movieEntity.getLabel();
        Call<ResultEntity> yourLikesService;
        if(label == null){
            String category = movieEntity.getCategory().equals("轮播") ? null : movieEntity.getCategory();
            yourLikesService = RequestUtils.getInstance().getTopMovieList(movieEntity.getClassify(),category);
        }else{
            yourLikesService = RequestUtils.getInstance().getYourLikes(label, movieEntity.getClassify());
        }
        yourLikesService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieEntity> movieEntityList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieEntity.class);
                CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailActivity.this);  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.detail_yourlikes_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(categoryRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("获取猜你想看失败");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 获取推荐列表
     * @date: 2021-12-11 12:11
     */
    private void getRecommend(){
        Call<ResultEntity> userData = RequestUtils.getInstance().getRecommend(movieEntity.getClassify());
        userData.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieEntity> movieEntityList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieEntity.class);
                CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList);
                LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailActivity.this);  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.detail_recommend_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(categoryRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("获取推荐列表失败");
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
            Context context = getBaseContext();
            Intent intent = new Intent(context, MoviePlayActivity.class);
            intent.putExtra("movieItem",movieItemString);
            context.startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "暂无播放资源", Toast.LENGTH_SHORT).show();
        }
    }
}
