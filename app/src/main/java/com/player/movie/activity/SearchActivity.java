package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.player.movie.R;
import com.player.movie.adapter.SearchRecyclerViewAdapter;
import com.player.movie.database.SearchWordDatabase;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.SearchWordEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.view.FlowLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,View.OnAttachStateChangeListener{
    MovieEntity movieEntity;
    EditText editText;
    ImageView clearImg;
    RecyclerView searchRecyclerView;
    SearchWordDatabase database;
    LinearLayout searchRecordLayout;
    boolean searching = false;
    int pageSize = 20;
    int pageNum = 1;
    List<MovieEntity>searchMovieList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
        getSearchRecordData();
    }

    /**
     * @author: wuwenqiangl
     * @description: 初始化UI
     * @date: 2022-08-23 22:06
     */
    private void initUI(){
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
        searchRecordLayout = findViewById(R.id.search_record_layout);
        findViewById(R.id.search_btn).setOnClickListener(this);// 搜索按钮的点击事件
        TextView searchTitle = findViewById(R.id.search_record_title).findViewById(R.id.module_title);
        searchTitle.setText(R.string.search_record);
    }

    /**
     * @author: wuwenqiangl
     * @description: 从sqlite中获取搜索记录
     * @date: 2022-08-24 23:00
     */
    private void getSearchRecordData(){
        new Thread(() -> {
            if(database == null)database = SearchWordDatabase.getInstance(this);
            List<SearchWordEntity> searchWordList = database.searchWordDao().query();
            LinearLayout searchRecordList = findViewById(R.id.search_record_list);
            TextView noData = findViewById(R.id.search_record_no_data);
            searchRecordList.removeAllViews();
            if(searchWordList.size() == 0){
                searchRecordList.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }else{
                noData.setVisibility(View.GONE);
                for(SearchWordEntity searchWordEntity:searchWordList){
                    TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.search_record_item, searchRecordList, false);
                    textView.setText(searchWordEntity.getMovieName());
                    searchRecordList.addView(textView);
                }
            }
        }).start();
    }

    /**
     * @author: wuwenqiangl
     * @description: 先删除再插入记录
     * @date: 2022-08-24 23:09
     */
    private void insertSearchRecord(String keyword){
        new Thread(()->{
            SearchWordEntity searchWordEntity = new SearchWordEntity();
            searchWordEntity.setMovieName(keyword);
            searchWordEntity.setClassify(movieEntity.getClassify());
            database.searchWordDao().delete(searchWordEntity);
            searchWordEntity.setCreatTime(new Date());
            database.searchWordDao().insert(searchWordEntity);
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_clear:
                editText.setText("");
                searchRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.search_btn:
                searching = true;
                searchRecordLayout.setVisibility(View.GONE);
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
                insertSearchRecord(keyword);
                break;
        }
    }

    /**
     * @author: wuwenqiangl
     * @description: 监听搜索框内容变化
     * @date: 2022-08-24 23:17
     */
    @Override
    public void onViewAttachedToWindow(View v) {
        if("".equals(editText.getText().toString())){// 如果搜索框内容为空，显示搜索记录
            clearImg.setVisibility(View.GONE);
            searchRecordLayout.setVisibility(View.VISIBLE);
            if(searching){// 如果是已经搜素过，重新获取搜索记录
                getSearchRecordData();
                searching = true;
            }
        }else{
            searchRecordLayout.setVisibility(View.GONE);
            clearImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }

    /**
     * @author: wuwenqiangl
     * @description: 设置搜索列表
     * @date: 2022-08-23 22:06
     */
    private void setSearchList(){
        searchRecyclerView.setVisibility(View.VISIBLE);
        searchRecyclerView.setAdapter(new SearchRecyclerViewAdapter(searchMovieList,this));
    }
}
