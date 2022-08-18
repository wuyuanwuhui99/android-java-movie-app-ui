package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.player.movie.R;
import com.player.movie.adapter.UrlGridViewAdapter;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.MovieUrlEntity;
import com.player.movie.fragment.LikeMovieFragment;
import com.player.movie.fragment.RecommendMovieFragment;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.myinterface.UrlClickListener;
import com.player.movie.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePlayActivity extends AppCompatActivity {
    View view;
    MovieEntity movieEntity;
    List<List<MovieUrlEntity>> playGroup;
    TextView prevUrlText = null;
    View prevUrlGrid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
        initData();
        addFraction();
        getMovieUrl();
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
        //设置剧情
        TextView plotTextView = view.findViewById(R.id.play_plot);
        plotTextView.setText(movieEntity.getPlot());
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
        TextView plot = view.findViewById(R.id.play_plot_title).findViewById(R.id.module_title);
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
                .replace(R.id.play_recommend_layout, new RecommendMovieFragment(movieEntity))
                .commit();
    }

    /**
     * @author: wuwenqiangl
     * @description: 初始化电影播放页数据
     * @date: 2021-12-04 15:59
     */
    private void getMovieUrl(){
        Call<ResultEntity> call = RequestUtils.getInstance().getMovieUrl(movieEntity.getMovieId());
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
                setTabFragment();
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
    private void setTabFragment(){
        String [] mTitles = new String[playGroup.size()];
        SegmentTabLayout tabLayout = view.findViewById(R.id.play_tab);

        LinearLayout playUrlLayout = view.findViewById(R.id.play_url_layout);
        for (int i=0;i<playGroup.size();i++){
            mTitles[i] = "播放地址"+(i+1);
            LayoutInflater inflater = LayoutInflater.from(this);
            NoScrollGridView gridView = (NoScrollGridView) inflater.inflate(R.layout.url_grid_layout,playUrlLayout,false);
            int finalI = i;
            @SuppressLint("ResourceType")
            UrlClickListener urlClickListener = textView ->{
                textView.setBackgroundResource(R.drawable.sm_active_border_decoration);
                textView.setTextColor(Color.parseColor(getString(R.color.navigate_active)));
                int position = textView.getId();
                // 当前播放的影片的实例独享
                MovieUrlEntity movieUrlEntity = playGroup.get(finalI).get(position);
                if(prevUrlText != null){
                    prevUrlText.setTextColor(Color.GRAY);
                    prevUrlText.setBackgroundResource(R.drawable.sm_border_decoration);
                }
                prevUrlText = textView;
            };
            gridView.setAdapter(new UrlGridViewAdapter(playGroup.get(i),this,playUrlLayout.getMeasuredWidth(),urlClickListener));
            if(i!=0){
                playUrlLayout.addView(gridView);
            }else{
                prevUrlGrid = gridView;
            }
        }

        tabLayout.setTabData(mTitles);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                View activeUrlGrid =  playUrlLayout.getChildAt(position);
                activeUrlGrid.setVisibility(View.VISIBLE);
                prevUrlGrid.setVisibility(View.GONE);
                prevUrlGrid = activeUrlGrid;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
