package com.player.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.player.R;

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
            isInit = true;
        }
        return view;
    }
}
