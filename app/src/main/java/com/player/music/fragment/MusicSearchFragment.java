package com.player.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.R;
import com.player.common.Constants;
import com.player.movie.BaseApplication;
import com.player.movie.api.Api;
import com.player.movie.entity.UserEntity;
import com.player.http.RequestUtils;
import com.player.http.ResultEntity;
import com.player.music.activity.MusicSearchActivity;
import com.player.music.entity.MusicEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicSearchFragment extends Fragment {
    View view;
    LinearLayout avaterLayout;
    MusicEntity musicEntity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_search,container,false);
        initData();
        getKeyWord();
        addSearchClickListener();
        return view;
    }

    private void initData(){
        avaterLayout = view.findViewById(R.id.music_avater_layout);
        avaterLayout.setVisibility(View.VISIBLE);
        UserEntity userEntity = BaseApplication.getInstance().getUserEntity();
        RoundedImageView avaterImage = view.findViewById(R.id.music_avater);
        if(userEntity.getAvater()!= null){
            Glide.with(getContext()).load(Constants.HOST + userEntity.getAvater()).into(avaterImage);
        }else{
            avaterImage.setImageResource(R.mipmap.default_avater);
        }
    }

    private void addSearchClickListener(){
        LinearLayout searchLayout = view.findViewById(R.id.music_search_layout);
        searchLayout.setOnClickListener(listener->{
            Context context = getContext();
            Intent intent = new Intent(context, MusicSearchActivity.class);
            intent.putExtra("musicItem", JSON.toJSONString(musicEntity));
            startActivity(intent);
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 获取搜索栏关键词
     * @date: 2024-04-25 23:20
     */
    public void getKeyWord(){
        Call<ResultEntity> getKeyWordService = RequestUtils.getMusicInstance().getKeywordMusic();
        getKeyWordService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                musicEntity = JSON.parseObject(JSON.toJSONString(response.body().getData()), MusicEntity.class);
                TextView textView = avaterLayout.findViewById(R.id.music_search_key);
                textView.setText(musicEntity.getSongName());
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {

            }
        });
    }
}
