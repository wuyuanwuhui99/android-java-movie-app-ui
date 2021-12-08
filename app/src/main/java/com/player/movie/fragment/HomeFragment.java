package com.player.movie.fragment;

import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.CategoryEntity;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.utils.SharedPreferencesUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        getUserData();
        return view;
    }


    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2021-12-04 15:59
     */
    private void getUserData(){
        Call<ResultEntity> userData = RequestUtils.getInstance().getUserData();
        userData.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                Gson gson = new Gson();
                UserEntity userEntity = gson.fromJson(gson.toJson(response.body().getData()),UserEntity.class);
                String avaterUrl = Api.HOST + userEntity.getAvater();
                System.out.println(avaterUrl);
                SharedPreferencesUtils.setParam("token",response.body().getToken());
                Glide.with(getContext()).load(avaterUrl).into((RoundedImageView)view.findViewById(R.id.avater));
                getBannerData();
                getAllCategoryListByPageName();
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
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
                Banner banner = view.findViewById(R.id.banner);
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
                System.out.println("错误");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 按页面获取要展示的category小类
     * @date: 2021-12-07 23:32
     */
    public void getAllCategoryListByPageName(){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getAllCategoryListByPageName("首页");
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<CategoryEntity> categoryEntities = JSON.parseArray(JSON.toJSONString(response.body().getData()), CategoryEntity.class);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                for(CategoryEntity categoryEntity:categoryEntities){
                    CategoryFragment categoryFragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("category", categoryEntity.getCategory());
                    bundle.putString("classify", categoryEntity.getClassify());
                    categoryFragment.setArguments(bundle);
                    transaction.add(R.id.category_layout, categoryFragment);
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
