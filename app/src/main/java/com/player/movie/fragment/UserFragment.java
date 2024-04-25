package com.player.movie.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.BaseApplication;
import com.player.R;
import com.player.movie.activity.UserActivity;
import com.player.movie.activity.WebViewActivity;
import com.player.movie.adapter.CategoryRecyclerViewAdapter;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.entity.UserEntity;
import com.player.http.RequestUtils;
import com.player.http.ResultEntity;
import com.player.movie.receiver.UpdateUserReciver;
import com.player.music.activity.MusicActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment implements View.OnClickListener {
    private View view;
    private UpdateUserReciver reciver;
    private boolean isUserVisibleHint = false;
    private boolean isExpandRecord = true;// 是否展开播放记录，默认展开
    private boolean isExpandFavorite = false;// 是否展开收藏记录
    private boolean isExpandView = false;// 是否展开浏览记录的箭头
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_user,container,false);
        }
        if(isUserVisibleHint){// 从第三个tab页切换到第四个tab页，加载了view但可能没显示
            initData();
        }
        return view;
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化组件
     * @date: 2021-12-11 12:11
     */
    @Override
    public void setUserVisibleHint(boolean isUserVisibleHint){
        super.setUserVisibleHint(isUserVisibleHint);
        this.isUserVisibleHint = getUserVisibleHint();
        if(this.isUserVisibleHint && view != null){// 从第一个tab直接切花到第四个tab页，显示了但可能没有加载view
            initData();
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化广播
     * @date: 2023-04-20 23:13
     */
    private void initReceiver(){
        reciver = new UpdateUserReciver();
        reciver.setOnReceivedMessageListener(userEntity -> setUserData());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UpdateUserReciver.TAG);
        intentFilter.setPriority(Integer.MAX_VALUE);
        getContext().registerReceiver(reciver,intentFilter);
    }

    private void initData(){
        setUserData();
        getUserMsg();
        getMoviList(RequestUtils.getMovieInstance().getPlayRecord(),R.id.play_record_list,R.id.no_data_play);
        addClickListener();
        initReceiver();
    }
    
    /**
     * @author: wuwenqiang
     * @description: 设置用户信息
     * @date: 2021-12-12 19:41
     */
    private void setUserData(){
        UserEntity userEntity = BaseApplication.getInstance().getUserEntity();
        RoundedImageView avaterImage = (RoundedImageView)view.findViewById(R.id.user_avater);
        if("".equals(userEntity.getAvater()) || userEntity.getAvater() == null){
            avaterImage.setImageResource(R.mipmap.default_avater);
        }else{
            Glide.with(getContext()).load(Api.HOST + userEntity.getAvater()).into(avaterImage);
        }
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
    private void getUserMsg(){
        Call<ResultEntity> categoryListService = RequestUtils.getMovieInstance().getUserMsg();
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
     * @description: 获取电影播放记录、浏览记录和收藏列表
     * @date: 2021-12-12 19:41
     */
    private void getMoviList(Call<ResultEntity> categoryListService, int resId,int noDataResId){
        loading = true;
        categoryListService.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                ResultEntity body = response.body();
                loading = false;
                if(body == null || ((ArrayList)body.getData()).size() == 0){
                    view.findViewById(noDataResId).setVisibility(View.VISIBLE);
                    view.findViewById(resId).setVisibility(View.GONE);
                }else{
                    List<MovieEntity> movieEntityList = JSON.parseArray(JSON.toJSONString(response.body().getData()),MovieEntity.class);
                    CategoryRecyclerViewAdapter recyclerViewAdapter = new CategoryRecyclerViewAdapter(movieEntityList,getContext());
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    RecyclerView recyclerView = view.findViewById(resId);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    view.findViewById(noDataResId).setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
                loading = false;
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
        view.findViewById(R.id.icon_record_arrow).setOnClickListener(this);
        view.findViewById(R.id.icon_favorite_arrow).setOnClickListener(this);
        view.findViewById(R.id.icon_view_arrow).setOnClickListener(this);
        view.findViewById(R.id.icon_music).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
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

            case R.id.icon_record_arrow:// 点击观看记录的折叠箭头
                if (loading)return;
                if(isExpandRecord){
                    // 如果是展开状态，点击后收起
                    isExpandRecord = false;
                    view.findViewById(R.id.icon_record_arrow).setRotation(0);
                    view.findViewById(R.id.play_record_list).setVisibility(View.GONE);
                    view.findViewById(R.id.no_data_play).setVisibility(View.GONE);
                }else{
                    // 如果是收起状态，点击后展开
                    isExpandRecord = true;
                    // 展开后重新获取播放记录
                    getMoviList(RequestUtils.getMovieInstance().getPlayRecord(),R.id.play_record_list,R.id.no_data_play);
                    view.findViewById(R.id.icon_record_arrow).setRotation(90);
                    view.findViewById(R.id.play_record_list).setVisibility(View.VISIBLE);
                }
                break;

            case R.id.icon_favorite_arrow:// 点击浏览记录的折叠箭头
                if (loading)return;
                if(isExpandFavorite){
                    // 如果是展开状态，点击后收起
                    isExpandFavorite = false;
                    view.findViewById(R.id.icon_favorite_arrow).setRotation(0);
                    view.findViewById(R.id.my_favorite_list).setVisibility(View.GONE);
                    view.findViewById(R.id.no_data_favorite).setVisibility(View.GONE);
                }else{
                    // 如果是收起状态，点击后展开
                    isExpandFavorite = true;
                    getMoviList(RequestUtils.getMovieInstance().getFavoriteList(),R.id.my_favorite_list,R.id.no_data_favorite);
                    view.findViewById(R.id.icon_favorite_arrow).setRotation(90);
                    view.findViewById(R.id.my_favorite_list).setVisibility(View.VISIBLE);
                }
                break;

            case R.id.icon_view_arrow:// 点击浏览记录的折叠箭头
                if (loading)return;
                if(isExpandView){
                    // 如果是展开状态，点击后收起
                    isExpandView = false;
                    view.findViewById(R.id.icon_view_arrow).setRotation(0);
                    view.findViewById(R.id.my_view_list).setVisibility(View.GONE);
                    view.findViewById(R.id.no_data_view).setVisibility(View.GONE);
                }else{
                    // 如果是收起状态，点击后展开
                    isExpandView = true;
                    getMoviList(RequestUtils.getMovieInstance().getViewRecord(),R.id.my_view_list,R.id.no_data_view);
                    view.findViewById(R.id.icon_view_arrow).setRotation(90);
                    view.findViewById(R.id.my_view_list).setVisibility(View.VISIBLE);
                }
                break;

            case R.id.icon_music:
                intent = new Intent(getContext(), MusicActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(reciver);
    }
}
