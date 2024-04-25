package com.player.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.player.R;
import com.player.movie.fragment.SearchFragment;

public class MusicHomeFragment extends Fragment {
    private View view;
    private boolean isInit = false;
    FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!isInit){
            view = inflater.inflate(R.layout.fragment_music_home,container,false);
            transaction = getFragmentManager().beginTransaction();
            addSearchFraction();
            isInit = true;
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 设置搜索和头像
     * @date: 2022-08-13 11:19
     */
    private void addSearchFraction(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.music_search_layout,new MusicSearchFragment())
                .commit();
    }
}
