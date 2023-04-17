package com.player.movie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.BaseApplication;
import com.player.movie.R;
import com.player.movie.activity.UserActivity;
import com.player.movie.activity.WebViewActivity;
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends MyFragment implements View.OnClickListener {
    View view;
    boolean isInit = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_user,container,false);
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化组件
     * @date: 2021-12-11 12:11
     */
    @Override
    public void initData(){
        if(isInit)return;
        isInit = true;
        setUserData();
        getUserMsg();
        getPlayRecord();
        addClickListener();
    }

    /**
     * @author: wuwenqiang
     * @description: 设置用户信息
     * @date: 2021-12-12 19:41
     */
    public void setUserData(){
        UserEntity userEntity = BaseApplication.getInstance().getUserEntity();
        Glide.with(getContext()).load(Api.HOST + userEntity .getAvater()).into((RoundedImageView)view.findViewById(R.id.user_avater));
        TextView username = view.findViewById(R.id.username);
        username.setText(userEntity.getUsername());
        if(userEntity .getSign() != null){
            TextView sign = view.findViewById(R.id.sign);
            sign.setText(userEntity.getSign());
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户四个指标信息，使用天数，关注，观看记录，浏览记录
     * @date: 2021-12-12 19:41
     */
    public void getUserMsg(){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getUserMsg();
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                Map<String,String> userMsg = JSON.parseObject(JSON.toJSONString(response.body().getData()), Map.class);
                TextView userAge = view.findViewById(R.id.user_age);
                userAge.setText(userMsg.get("userAge"));

                TextView favoriteCount = view.findViewById(R.id.favorite_count);
                favoriteCount.setText(userMsg.get("favoriteCount"));

                TextView playRecordCount = view.findViewById(R.id.play_record_count);
                playRecordCount.setText(userMsg.get("playRecordCount"));

                TextView viewRecordCount = view.findViewById(R.id.view_record_count);
                viewRecordCount.setText(userMsg.get("viewRecordCount"));
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户四个指标信息，使用天数，关注，观看记录，浏览记录
     * @date: 2021-12-12 19:41
     */
    public void getPlayRecord(){
        Call<ResultEntity> categoryListService = RequestUtils.getInstance().getPlayRecord();
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                List<MovieEntity> movieEntityList = JSON.parseArray(JSON.toJSONString(response.body().getData()),MovieEntity.class);
                CategoryRecyclerViewAdapter recyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList,getContext());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecyclerView recyclerView = view.findViewById(R.id.play_record_list);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 点击头像编辑按钮，跳转到用户信息页面
     * @date: 2022-08-30 22:39
     */
    private void addClickListener(){
        view.findViewById(R.id.icon_edit).setOnClickListener(this);
        view.findViewById(R.id.movie_circle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.icon_edit:
                intent = new Intent(getContext(), UserActivity.class);
                startActivity(intent);
                break;
            case R.id.movie_circle:
                intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url","http://192.168.0.103:3003/#/?_t="+ Math.random());
                startActivity(intent);
                break;
        }
    }
}
