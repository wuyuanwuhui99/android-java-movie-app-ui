package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.player.R;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.MovieUrlEntity;
import com.player.movie.fragment.LikeMovieFragment;
import com.player.movie.fragment.RecommendMovieFragment;
import com.player.http.RequestUtils;
import com.player.http.ResultEntity;
import com.player.movie.view.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePlayActivity extends AppCompatActivity implements View.OnClickListener{
    MovieEntity movieEntity;
    List<List<MovieUrlEntity>> playGroup;
    View prevUrlTabLayout = null;
    TextView prevUrlText = null;
    MovieUrlEntity actvieMovieUrlEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
        initData();
        addFraction();
        getMovieUrl();
        savePlayRecord();
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化电影播放页数据
     * @date: 2021-12-04 15:59
     */
    private void initData(){
        Intent intent = getIntent();
        movieEntity = JSON.parseObject(intent.getStringExtra("movieItem"), MovieEntity.class);
        //设置剧情
        TextView plotTextView = findViewById(R.id.play_plot);
        plotTextView.setText(movieEntity.getPlot());
        //设置电影名称
        TextView movieName = findViewById(R.id.play_movie_name);
        movieName.setText(movieEntity.getMovieName());
        //设置主演
        TextView movieStar = findViewById(R.id.play_star);
        if(movieEntity.getStar() != null){
            movieStar.setText(movieEntity.getStar());
        }else{
            movieStar.setText("主演：暂无");
        }

        //计算得分和星星
        LinearLayout scoreLayout = findViewById(R.id.play_score_layout);
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
            TextView scoreText = findViewById(R.id.play_score);
            scoreText.setText(score.toString());
        }else{
            findViewById(R.id.play_no_score).setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.GONE);
        }
        TextView plot = findViewById(R.id.play_plot_title).findViewById(R.id.module_title);
        plot.setText(R.string.detail_plot);
    }

    /**
     * @author: wuwenqiang
     * @description: 添加推荐和猜你想看模块
     * @date: 2022-08-18 22:05
     */
    private void addFraction(){
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.play_your_like_layout, new LikeMovieFragment(movieEntity))
                .replace(R.id.play_recommend_layout, new RecommendMovieFragment(movieEntity,"horizontal"))
                .commit();
    }

    /**
     * @author: wuwenqiangl
     * @description: 初始化电影播放页数据
     * @date: 2021-12-04 15:59
     */
    private void getMovieUrl(){
        Call<ResultEntity> call = RequestUtils.getMovieInstance().getMovieUrl(100L);
//        Call<ResultEntity> call = RequestUtils.getMovieInstance().getMovieUrl(movieEntity.getMovieId());
        call.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieUrlEntity> movieUrlList = JSON.parseArray(JSON.toJSONString(response.body().getData()), MovieUrlEntity.class);
                playGroup = new ArrayList<>();
                for (MovieUrlEntity movieUrlEntity:movieUrlList){
                    List <MovieUrlEntity> group;
                    if(playGroup.size() >= movieUrlEntity.getPlayGroup()){
                        group = playGroup.get(movieUrlEntity.getPlayGroup()-1);
                    }else{
                        group = new ArrayList<>();
                    }
                    if(group.size() == 0){
                        playGroup.add(group);
                    }
                    group.add(movieUrlEntity);
                }
                setTab();
;            }
            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {

            }
        });
    }



    /**
     * @author: wuwenqiang
     * @description: 设置播放tab
     * @date: 2022-1-20 23:26
     */
    private void setTab(){
        String [] mTitles = new String[playGroup.size()];
        LinearLayout playUrlLayout = findViewById(R.id.play_url_layout);
        for (int i=0;i<playGroup.size();i++){
            mTitles[i] = "播放地址"+(i+1);
            LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.url_linear_layout, playUrlLayout, false);
            List<MovieUrlEntity> movieUrlEntityList = playGroup.get(i);
            int rows = (int) Math.ceil(movieUrlEntityList.size()/5);
            for(int j = 0; j < rows; j++){
                View rowView = LayoutInflater.from(this).inflate(R.layout.url_row, tabLinearLayout, false);
                TextView []textView = new TextView[]{
                        rowView.findViewById(R.id.url_btn_0),
                        rowView.findViewById(R.id.url_btn_1),
                        rowView.findViewById(R.id.url_btn_2),
                        rowView.findViewById(R.id.url_btn_3),
                        rowView.findViewById(R.id.url_btn_4)
                };
                // 如果是第一个tab而且是一个text，设置成激活状态
                if(j == 0 && i == 0){
                    textView[0].setBackgroundResource(R.drawable.sm_active_border_decoration);
                    textView[0].setTextColor( getResources().getColor(R.color.navigate_active));
                    prevUrlText = textView[0];
                    actvieMovieUrlEntity = movieUrlEntityList.get(0);
                }
                for(int k = 0; k < 5; k++){
                    if(j * 5 + k < movieUrlEntityList.size()){
                        MovieUrlEntity movieUrlEntity = movieUrlEntityList.get(j * 5 + k);
                        textView[k].setTag(movieUrlEntity);
                        textView[k].setText(movieUrlEntity.getLabel());
                        textView[k].setTag(movieUrlEntity);
                        textView[k].setOnClickListener(this::onClick);
                    }else {
                        textView[k].setVisibility(View.GONE);
                    }
                }
                tabLinearLayout.addView(rowView);
            }
            if(i==0){
                prevUrlTabLayout = tabLinearLayout;
            }else{
                tabLinearLayout.setVisibility(View.GONE);
            }
            playUrlLayout.addView(tabLinearLayout);
        }
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabData(mTitles);
        tabLayout.setOnTabSelectListener(position -> {
            View activeUrlGrid =  playUrlLayout.getChildAt(position);
            activeUrlGrid.setVisibility(View.VISIBLE);
            prevUrlTabLayout.setVisibility(View.GONE);
            prevUrlTabLayout = activeUrlGrid;
        });
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView)v;
        textView.setBackgroundResource(R.drawable.sm_active_border_decoration);
        textView.setTextColor( getResources().getColor(R.color.navigate_active));

        prevUrlText.setBackgroundResource(R.drawable.sm_border_decoration);
        prevUrlText.setTextColor(getResources().getColor(R.color.navigate));
        prevUrlText = textView;

        actvieMovieUrlEntity = (MovieUrlEntity) textView.getTag();
    }

    /**
     * @author: wuwenqiang
     * @description: 插入播放记录
     * @date: 2021-12-22 23:13
     */
    public void savePlayRecord(){
        Call<ResultEntity> call = RequestUtils.getMovieInstance().savePlayRecord(movieEntity);
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
