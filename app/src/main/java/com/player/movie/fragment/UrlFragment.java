package com.player.movie.fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.movie.R;
import com.player.movie.adapter.FlowAdapter;
import com.player.movie.adapter.UrlRecyclerViewAdapter;
import com.player.movie.entity.MovieUrlEntity;
import com.player.movie.view.FlowLayout;

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
        FlowAdapter adapter = new FlowAdapter(getContext(), movieUrlEntities);
        FlowLayout flowLayout = view.findViewById(R.id.url_flow);
        flowLayout.setAdapter(adapter);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        FlowLayout flowLayout = view.findViewById(R.id.url_flow);
//        for(MovieUrlEntity movieUrlEntity:movieUrlEntities){
//            TextView textView = (TextView) inflater.inflate(R.layout.url_item,container,false);
//            textView.setText(movieUrlEntity.getLabel());
//            flowLayout.addView(textView,layoutParams);
//        }
//        RecyclerView recyclerView = view.findViewById(R.id.url_recycler_view);
//        UrlRecyclerViewAdapter urlItemsAdapter = new UrlRecyclerViewAdapter(getContext(),movieUrlEntities);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(urlItemsAdapter);
        return view;
    }
}
