package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.player.movie.R;
import com.player.movie.entity.UserEntity;
import com.player.movie.fragment.HomeFragment;
import com.player.movie.fragment.MovieFragment;
import com.player.movie.fragment.TVFragment;
import com.player.movie.fragment.UserFragment;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.state.State;
import com.player.movie.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;//容器
    private List<Fragment> listFragment = new ArrayList<Fragment>();

    //导航栏布局栏
    LinearLayout homeLinearLayout;
    LinearLayout movieLinearLayout;
    LinearLayout tvLinearLayout;
    LinearLayout userLinearLayout;

    //导航栏图标
    ImageView homeImg;
    ImageView movieImg;
    ImageView  tvImg;
    ImageView userImg;

    //导航栏文字
    TextView homeText;
    TextView movieText;
    TextView tvText;
    TextView userText;

    //初始化4个切换页
    HomeFragment homeFragment;
    MovieFragment movieFragment;
    TVFragment tvFragment;
    UserFragment userFragment;

    int tabIds[]={R.id.home,R.id.movie,R.id.tv,R.id.user_center};
    int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserData();
    }

    private void initView(){
        viewPager = findViewById(R.id.viewpager);

        //初始化4个切换页
        homeFragment = new HomeFragment();
        movieFragment = new MovieFragment();
        tvFragment = new TVFragment();
        userFragment = new UserFragment();

        //把4个切换页添加到容器内
        listFragment.add(homeFragment);
        listFragment.add(movieFragment);
        listFragment.add(tvFragment);
        listFragment.add(userFragment);

        //导航栏布局栏
        homeLinearLayout = findViewById(R.id.home);
        movieLinearLayout = findViewById(R.id.movie);
        tvLinearLayout = findViewById(R.id.tv);
        userLinearLayout = findViewById(R.id.user_center);

        //导航栏图标
        homeImg = findViewById(R.id.home_img);
        movieImg = findViewById(R.id.movie_img);
        tvImg = findViewById(R.id.tv_img);
        userImg = findViewById(R.id.user_img);

        //导航栏文字
        homeText = findViewById(R.id.home_text);
        movieText = findViewById(R.id.movie_text);
        tvText = findViewById(R.id.tv_text);
        userText = findViewById(R.id.user_text);
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2021-12-04 15:59
     */
    private void getUserData(){
        Call<ResultEntity> userData = RequestUtils.getInstance().getUserData();
        userData.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                Gson gson = new Gson();
                State.userEntity = gson.fromJson(gson.toJson(response.body().getData()),UserEntity.class);
                SharedPreferencesUtils.setParam("token",response.body().getToken());
                findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
                initView();//初始化view
                initEvent();//初始化事件
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                System.out.println("错误");
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化点击事件
     * @date: 2021-12-11 11:08
     */
    private void initEvent(){
        FragmentPagerAdapter pageAdapter =new FragmentPagerAdapter(getSupportFragmentManager()) {  //适配器直接new出来
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);//直接返回
            }

            @Override
            public int getCount() {
                return listFragment.size(); //放回tab数量
            }
        };

        //初始化切换
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  //监听界面拖动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                int currentItem=viewPager.getCurrentItem(); //获取当前界面
                tab(currentItem); //切换图标亮度
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        homeLinearLayout.setOnClickListener(this);
        movieLinearLayout.setOnClickListener(this);
        tvLinearLayout.setOnClickListener(this);
        userLinearLayout.setOnClickListener(this);
    }

    /**
     * @author: wuwenqiang
     * @description: 底部导航切换
     * @date: 2021-12-11 11:08
     */
    private void tab(int i){  //用于屏幕脱拖动时切换底下图标，只在监听屏幕拖动中调用
        int color = getResources().getColor(R.color.navigate_active);
        resetTab();
        switch (i){
            case 0:{
                homeImg.setImageResource(R.mipmap.icon_home_active);
                homeText.setTextColor(color);
                break;
            }
            case 1:
            {
                movieImg.setImageResource(R.mipmap.icon_movie_active);
                movieText.setTextColor(color);
                movieFragment.initData();
                break;
            }
            case 2:
            {
                tvImg.setImageResource(R.mipmap.icon_tv_active);
                tvText.setTextColor(color);
                tvFragment.initData();
                break;
            }
            case 3:
            {
                userImg.setImageResource(R.mipmap.icon_user_active);
                userText.setTextColor(color);
                userFragment.initData();
                break;
            }
        }
    }

    //自定义一个方法
    private void setSelect(int i){
        viewPager.setCurrentItem(i);//切换界面
        tabIndex = i;
    }

    //导航栏的点击事件
    @Override
    public void onClick(View view) {  //设置点击的为；亮色
        if(view.getId() == tabIds[tabIndex])return;
        resetTab();
        switch (view.getId()){
            case R.id.home:{
                setSelect(0);
                homeImg.setImageResource(R.mipmap.icon_home_active);
                break;
            }
            case R.id.movie:
            {
                setSelect(1);
                movieImg.setImageResource(R.mipmap.icon_movie_active);
                break;
            }
            case R.id.tv:
            {
                setSelect(2);
                tvImg.setImageResource(R.mipmap.icon_tv_active);
                break;
            }
            case R.id.user_center:
            {
                setSelect(3);
                userImg.setImageResource(R.mipmap.icon_user_active);
                break;
            }

        }
    }

    /**
     * @author: wuwenqiang
     * @description: 重置底部导航暗色
     * @date: 2021-12-11 11:08
     */
    private void resetTab() {
        homeImg.setImageResource(R.mipmap.icon_home);
        movieImg.setImageResource(R.mipmap.icon_movie);
        tvImg.setImageResource(R.mipmap.icon_tv);
        userImg.setImageResource(R.mipmap.icon_user);

        int color = this.getResources().getColor(R.color.navigate);
        homeText.setTextColor(color);
        movieText.setTextColor(color);
        tvText.setTextColor(color);
        userText.setTextColor(color);
    }
}
