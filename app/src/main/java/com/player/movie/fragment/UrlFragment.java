package com.player.movie.fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.player.movie.R;
import com.player.movie.adapter.PlayUrlPannelAdapter;
import com.player.movie.entity.MovieUrlEntity;

import java.util.List;


public class UrlFragment extends Fragment {
    View view;
    List<MovieUrlEntity> movieUrlEntities;
    public UrlFragment(List<MovieUrlEntity> movieUrlEntities) {
        this.movieUrlEntities = movieUrlEntities;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_url, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.movie_recycler_view);
        PlayUrlPannelAdapter playUrlPannelAdapter = new PlayUrlPannelAdapter(getContext(),movieUrlEntities);
        recyclerView.setAdapter(playUrlPannelAdapter);
        return view;
    }
}
