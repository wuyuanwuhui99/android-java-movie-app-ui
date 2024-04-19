package com.player.movie.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.player.R;
import com.player.movie.utils.CommonUtils;

public class TabLayout extends LinearLayout {
    private TextView prevText;
    private int prevIndex = 0;
    private String []titles;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化设置切换按钮
     * @date: 2022-08-22 22:59
     */
    @SuppressLint("WrongConstant")
    public void setTabData(String titles[]){
        this.titles = titles;
        if(titles.length > 1){
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            setLayoutParams(linearLayoutParams);
            setOrientation(LinearLayoutManager.HORIZONTAL);
            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < titles.length; i++){
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(textLayoutParams);
                textView.setText(titles[i]);
                addView(textView);
                if(i == 0){
                    prevText = textView;
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundResource(R.drawable.tab_left_active);
                }else if(i == titles.length - 1){
                    textView.setTextColor(getResources().getColor(R.color.background_blue));
                    textLayoutParams.leftMargin = -CommonUtils.dip2px(getContext(),R.dimen.border_size);
                    textView.setBackgroundResource(R.drawable.tab_right_normal);
                }else{
                    textView.setTextColor(getResources().getColor(R.color.background_blue));
                    textLayoutParams.leftMargin =-CommonUtils.dip2px(getContext(),R.dimen.border_size);
                    textView.setBackgroundResource(R.drawable.tab_middle_normal);
                }
            }
        }else{
            setVisibility(GONE);
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 设置切换按钮的点击事件
     * @date: 2022-08-22 22:58
     */
    public void setOnTabSelectListener(OnTabSelectListener tabSelectListener){
        if(titles.length > 1){
            for(int i = 0; i < titles.length; i++){
                int finalI = i;
                getChildAt(i).setOnClickListener(view->{
                    if(prevIndex == finalI)return;
                    TextView activeText = (TextView)view;
                    activeText.setTextColor(Color.WHITE);
                    // 设置激活状态
                    if(finalI == 0){
                        activeText.setBackgroundResource(R.drawable.tab_left_active);
                    }else if(finalI == titles.length - 1){
                        activeText.setBackgroundResource(R.drawable.tab_right_active);
                    }else{
                        activeText.setBackgroundResource(R.drawable.tab_middle_active);
                    }
                    // 设置非激活状态
                    prevText.setTextColor(getContext().getResources().getColor(R.color.navigate));
                    if(prevIndex == 0){
                        prevText.setBackgroundResource(R.drawable.tab_left_normal);
                    }else if(prevIndex == titles.length - 1){
                        prevText.setBackgroundResource(R.drawable.tab_right_normal);
                    }else{
                        prevText.setBackgroundResource(R.drawable.tab_middle_normal);
                    }
                    prevText = activeText;
                    prevIndex = finalI;
                    tabSelectListener.onTabSelect(finalI);
                });
            }
        }
    }

    public interface OnTabSelectListener {
        void onTabSelect(int activeIndex);
    }
}
