package com.player.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.player.R;
import com.player.movie.BaseApplication;
import com.player.movie.api.Api;
import com.player.movie.entity.CategoryEntity;
import com.player.movie.entity.MovieEntity;
import com.player.movie.fragment.CategoryFragment;
import com.player.movie.fragment.SearchFragment;
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

public class MusicCircleFragment extends Fragment {
    boolean isInit = false;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_music_circle,container,false);
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化调用方法
     * @date: 2024-04-19 23:30
     */
    @Override
    public void setUserVisibleHint(boolean isUserVisibleHint){
        super.setUserVisibleHint(isUserVisibleHint);
        if(getUserVisibleHint() && !isInit){
            isInit = true;

        }
    }
}
