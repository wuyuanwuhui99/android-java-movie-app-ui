package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.player.movie.R;
import com.player.movie.activity.MovieDetailActivity;
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.entity.MovieEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendMovieFragment extends Fragment {
    View view;
    MovieEntity movieEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fraction_recommend_movie,container,false);
        }
        getRecommend();
        setModuleTitle();
        return view;
    }

    public RecommendMovieFragment(MovieEntity movieEntity){
        this.movieEntity = movieEntity;
    }

    /**
     * @author: wuwenqiang
     * @description: 设置模块的标题
     * @date: 2022-08-18 22:27
     */
    private void setModuleTitle(){
        TextView yourLikeText = view.findViewById(R.id.recommend_title).findViewById(R.id.module_title);
        yourLikeText.setText(R.string.recommend);
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
                CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList, getContext());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.recommend_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(categoryRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("获取推荐列表失败");
            }
        });
    }
}
