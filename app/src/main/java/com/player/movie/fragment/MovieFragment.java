package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.CategoryEntity;
import com.player.movie.entity.MovieEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    boolean isInit = false;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_movie,container,false);
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化调用方法
     * @date: 2022-08-13 11:33
     */
    public void initData(){
        if(!isInit){
            isInit = true;
            addSearchFraction();
            getBannerData();
            getAllCategoryListByPageName();
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 设置搜索和头像
     * @date: 2022-08-13 11:19
     */
    private void addSearchFraction(){
        FragmentTransaction transaction =  getFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_search_layout,new SearchFragment(getResources().getString(R.string.movie)))
                .commit();
    }

    /**
     * @author: wuwenqiang
     * @description: 获取banner图片
     * @date: 2021-12-07 23:32
     */
    public void getBannerData(){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getCategoryList("轮播","电影");
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieEntity> movieEntity = JSON.parseArray(JSON.toJSONString(response.body().getData()),MovieEntity.class);
                Banner banner = view.findViewById(R.id.movie_banner);
                banner.setAdapter(new BannerImageAdapter<MovieEntity>(movieEntity) {
                    @Override
                    public void onBindView(BannerImageHolder holder, MovieEntity movieEntity, int position, int size) {
                        //图片加载自己实现
                        Glide.with(holder.imageView)
                                .load(Api.HOST + movieEntity.getLocalImg())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                }).setIndicator(new CircleIndicator(getContext())).setBannerRound(20.0f);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(call);
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 按页面获取要展示的category小类
     * @date: 2021-12-07 23:32
     */
    public void getAllCategoryListByPageName(){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getAllCategoryListByPageName("电影");
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<CategoryEntity> categoryEntities = JSON.parseArray(JSON.toJSONString(response.body().getData()), CategoryEntity.class);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                for(CategoryEntity categoryEntity:categoryEntities){
                    CategoryFragment categoryFragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("category", categoryEntity.getCategory());
                    bundle.putString("classify", categoryEntity.getClassify());
                    categoryFragment.setArguments(bundle);
                    transaction.add(R.id.movie_category_layout, categoryFragment);
                }
                transaction.commit();
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }
}
