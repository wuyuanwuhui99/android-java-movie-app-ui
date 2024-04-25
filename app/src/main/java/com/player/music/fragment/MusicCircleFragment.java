package com.player.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.player.R;

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
