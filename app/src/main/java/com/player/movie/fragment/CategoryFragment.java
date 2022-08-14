package com.player.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSON;
import com.player.movie.R;
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.entity.MovieEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_fragment,container,false);
        Bundle bundle = getArguments();
        //这里就拿到了之前传递的参数
        String category = bundle.getString("category");
        String classify = bundle.getString("classify");
        TextView textView = view.findViewById(R.id.category_title).findViewById(R.id.module_title);
        textView.setText(category);
        getCategoryData(category,classify);
        return view;
    }

    public void getCategoryData(String category,String classify){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getCategoryList(category,classify);
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieEntity> movieEntityList = JSON.parseArray(JSON.toJSONString(response.body().getData()),MovieEntity.class);
                CategoryRecyclerViewAdapter recyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList,getContext());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.movie_recycler_view);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }
}
