package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.player.movie.R;
import com.player.movie.adapter.SearchRecyclerViewAdapter;
import com.player.movie.entity.MovieEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,View.OnAttachStateChangeListener{
    MovieEntity movieEntity;
    EditText editText;
    ImageView clearImg;
    RecyclerView searchRecyclerView;
    int pageSize = 20;
    int pageNum = 1;
    List<MovieEntity>searchMovieList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        movieEntity = JSON.parseObject(intent.getStringExtra("movieItem"), MovieEntity.class);
        editText = findViewById(R.id.search_input);
        editText.addOnAttachStateChangeListener(this);
        editText.setHint(movieEntity.getMovieName());
        clearImg = findViewById(R.id.search_clear);
        clearImg.setOnClickListener(this);
        searchRecyclerView = findViewById(R.id.search_recycler_view);
        //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        findViewById(R.id.search_btn).setOnClickListener(this);// 搜索按钮的点击事件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_clear:
                editText.setText("");
                searchRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.search_btn:
                String keyword = editText.getText().toString();
                if("".equals(keyword)){
                    keyword = movieEntity.getMovieName();
                    editText.setText(keyword);
                    searchMovieList.add(movieEntity);
                    setSearchList();
                }else {
                    Call<ResultEntity> call = RequestUtils.getInstance().search(null,null,null,null,null,keyword,pageSize,pageNum);
                    call.enqueue(new Callback<ResultEntity>() {

                        @Override
                        public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                            searchMovieList = JSON.parseArray(JSON.toJSONString(response.body().getData()),MovieEntity.class);
                            setSearchList();
                        }

                        @Override
                        public void onFailure(Call<ResultEntity> call, Throwable t) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        if(editText.getText() == null || "".equals(editText.getText())){
            clearImg.setVisibility(View.GONE);
        }else{
            clearImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }

    /**
     * @author: wuwenqiangl
     * @description: 设置搜索列表
     * @date: 2021-08-23 22:06
     */
    private void setSearchList(){
        searchRecyclerView.setVisibility(View.VISIBLE);
        searchRecyclerView.setAdapter(new SearchRecyclerViewAdapter(searchMovieList,this));
    }
}
