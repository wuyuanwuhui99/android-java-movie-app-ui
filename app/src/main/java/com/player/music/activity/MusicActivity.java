package com.player.music.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.player.R;
import com.player.music.fragment.MusicCircleFragment;
import com.player.music.fragment.MusicHomeFragment;
import com.player.music.fragment.MusicRecommentFragment;
import com.player.music.fragment.MusicUserFragment;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();//初始化view
        initEvent();//初始化事件
    }

    private ViewPager viewPager;//容器
    private List<Fragment> listFragment = new ArrayList<>();

    // 当前选中的导航
    ImageView activeImage;
    TextView activeText;

    // 导航的id和图片
    int[] tabIds = {R.id.music_home, R.id.music_recommend, R.id.music_circle, R.id.music_my};
    int[] tabRes = {R.mipmap.icon_home, R.mipmap.icon_recomment, R.mipmap.icon_music_circle, R.mipmap.icon_user};
    int[] tabActiveRes = {R.mipmap.icon_home_active, R.mipmap.icon_recomment_active, R.mipmap.icon_music_circle_active, R.mipmap.icon_user_active};

    // 导航图片和文字
    LinearLayout[] tabLinearLayout = new LinearLayout[4];
    ImageView[] tabImageView = new ImageView[4];
    TextView[] tabTextView = new TextView[4];

    int activeIndex = 0;

    private void initView(){
        viewPager = findViewById(R.id.music_viewpager);

        //把4个切换页添加到容器内
        listFragment.add(new MusicHomeFragment());
        listFragment.add(null);// 适配器中动态添加，做懒加载用
        listFragment.add(null);
        listFragment.add(null);

        //导航栏布局栏
        tabLinearLayout[0] = findViewById(R.id.music_home);
        tabLinearLayout[0].setTag(0);
        tabLinearLayout[1] = findViewById(R.id.music_recommend);
        tabLinearLayout[1].setTag(1);
        tabLinearLayout[2] = findViewById(R.id.music_circle);
        tabLinearLayout[2].setTag(2);
        tabLinearLayout[3] = findViewById(R.id.music_my);
        tabLinearLayout[3].setTag(3);

        //导航栏图标
        activeImage = tabImageView[0] = findViewById(R.id.music_home_img);
        tabImageView[1] = findViewById(R.id.music_recommend_img);
        tabImageView[2] = findViewById(R.id.music_circle_img);
        tabImageView[3] = findViewById(R.id.music_my_img);

        //导航栏文字
        activeText = tabTextView[0] = findViewById(R.id.music_home_text);
        tabTextView[1] = findViewById(R.id.music_recommend_text);
        tabTextView[2] = findViewById(R.id.music_circle_text);
        tabTextView[3] = findViewById(R.id.music_my_text);
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
                Fragment fragment = listFragment.get(position);
                if(fragment == null){
                    if(position == 1){
                        fragment = new MusicRecommentFragment();
                    }else if(position == 2){
                        fragment = new MusicCircleFragment();
                    }else {
                        fragment = new MusicUserFragment();
                    }
                    listFragment.set(position,fragment);//直接返回
                }
                return fragment;
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
