package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.player.movie.R;
import com.player.movie.entity.UserEntity;

public class TVFragment extends Fragment {
    private UserEntity userEntity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_fragment,container,false);
        return view;
    }

    public TVFragment(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    public void initData(){
        System.out.println("TVFragment");
    }
}
