package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.UserEntity;

public class MovieFragment extends Fragment {
    private String avaterUrl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment,container,false);
        return view;
    }

    public MovieFragment(UserEntity userEntity){
        avaterUrl = Api.HOST + userEntity.getAvater();
    }

    public void initData(){
        System.out.println("MovieFragment");
    }
}
