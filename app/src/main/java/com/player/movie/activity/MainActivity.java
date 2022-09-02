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
import com.player.movie.fragment.MyFragment;
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
    private List<MyFragment> listFragment = new ArrayList<>();

    //初始化4个切换页
    HomeFragment homeFragment;
    MovieFragment movieFragment;
    TVFragment tvFragment;
    UserFragment userFragment;

    // 当前选中的导航
    ImageView activeImage;
    TextView activeText;

    // 导航的id和图片
    int[] tabIds = {R.id.home, R.id.movie, R.id.tv, R.id.user_center};
    int[] tabRes = {R.mipmap.icon_home, R.mipmap.icon_movie, R.mipmap.icon_tv, R.mipmap.icon_user};
    int[] tabActiveRes = {R.mipmap.icon_home_active, R.mipmap.icon_movie_active, R.mipmap.icon_tv_active, R.mipmap.icon_user_active};

    // 导航图片和文字
    LinearLayout[] tabLinearLayout = new LinearLayout[4];
    ImageView[] tabImageView = new ImageView[4];
    TextView[] tabTextView = new TextView[4];

    int activeIndex = 0;

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
        tabLinearLayout[0] = findViewById(R.id.home);
        tabLinearLayout[0].setTag(0);
        tabLinearLayout[1] = findViewById(R.id.movie);
        tabLinearLayout[1].setTag(1);
        tabLinearLayout[2] = findViewById(R.id.tv);
        tabLinearLayout[2].setTag(2);
        tabLinearLayout[3] = findViewById(R.id.user_center);
        tabLinearLayout[3].setTag(3);

        //导航栏图标
        activeImage = tabImageView[0] = findViewById(R.id.home_img);
        tabImageView[1] = findViewById(R.id.movie_img);
        tabImageView[2] = findViewById(R.id.tv_img);
        tabImageView[3] = findViewById(R.id.user_img);

        //导航栏文字
        activeText = tabTextView[0] = findViewById(R.id.home_text);
        tabTextView[1] = findViewById(R.id.movie_text);
        tabTextView[2] = findViewById(R.id.tv_text);
        tabTextView[3] = findViewById(R.id.user_text);
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
                SharedPreferencesUtils.setParam(MainActivity.this,"token",response.body().getToken());
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
                if(currentItem == activeIndex)return;
                tab(currentItem); //切换图标亮度
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        for(int i = 0; i < tabLinearLayout.length; i++){
            tabLinearLayout[i].setOnClickListener(this);
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 底部导航切换
     * @date: 2021-12-11 11:08
     */
    private void tab(int i){  //用于屏幕脱拖动时切换底下图标，只在监听屏幕拖动中调用
        // 清除上次的选中的杨思
        activeImage.setImageResource(tabRes[activeIndex]);
        activeText.setTextColor(getResources().getColor(R.color.navigate));

        // 当前选中的样式
        tabImageView[i].setImageResource(tabActiveRes[i]);
        tabTextView[i].setTextColor(getResources().getColor(R.color.navigate_active));

        // 记录当前选中的样式
        activeImage = tabImageView[i];
        activeText = tabTextView[i];
        activeIndex = i;

        listFragment.get(i).initData();
    }

    //导航栏的点击事件
    @Override
    public void onClick(View view) {  //设置点击的为；亮色
        if(view.getId() == tabIds[activeIndex])return;
        int index = (int) view.getTag();
        tab(index);
        viewPager.setCurrentItem(index);//切换界面
    }
}
