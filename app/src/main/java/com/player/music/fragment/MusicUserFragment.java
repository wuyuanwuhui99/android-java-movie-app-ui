package com.player.music.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.player.R;


public class MusicUserFragment extends Fragment {
    private View view;
    private boolean isUserVisibleHint = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_music_user,container,false);
        }
        if(isUserVisibleHint){// 从第三个tab页切换到第四个tab页，加载了view但可能没显示
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化组件
     * @date: 2024-04-19 23:31
     */
    @Override
    public void setUserVisibleHint(boolean isUserVisibleHint){
        super.setUserVisibleHint(isUserVisibleHint);
        this.isUserVisibleHint = getUserVisibleHint();
        if(this.isUserVisibleHint && view != null){// 从第一个tab直接切花到第四个tab页，显示了但可能没有加载view
        }
    }
}
