package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.state.State;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    View view;
    LinearLayout avaterLayout;
    String classify;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        initData();
        getKeyWord();
        return view;
    }

    public SearchFragment(String classify){
        this.classify = classify;
    }

    private void initData(){
        avaterLayout = view.findViewById(R.id.avater_layout);
        avaterLayout.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(Api.HOST + State.userEntity .getAvater()).into((RoundedImageView)view.findViewById(R.id.avater));
    }

    /**
     * @author: wuwenqiang
     * @description: 获取搜索栏关键词
     * @date: 2021-12-11 11:08
     */
    public void getKeyWord(){
        Call<ResultEntity> getKeyWordService = RequestUtils.getInstance().getKeyWord(classify);
        getKeyWordService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                Map map = JSON.parseObject(JSON.toJSONString(response.body().getData()), Map.class);
                String movieName = (String) map.get("movieName");
                TextView textView = avaterLayout.findViewById(R.id.search_key);
                textView.setText(movieName);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {

            }
        });
    }
}
