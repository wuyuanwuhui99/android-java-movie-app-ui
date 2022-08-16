package com.player.movie.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.movie.R;
import com.player.movie.entity.MovieUrlEntity;
import com.player.movie.myinterface.UrlClickListener;
import com.player.movie.utils.CommonUtils;
import com.player.movie.view.NoScrollGridView;

import java.util.List;

public class UrlGridViewAdapter extends BaseAdapter implements View.OnClickListener{

    private List<MovieUrlEntity>urlEntityList;
    private Context context;
    private int marginLeft;
    private int width;
    private int height;
    private UrlClickListener urlClickListener;
    private NoScrollGridView gridView;
    public UrlGridViewAdapter(List<MovieUrlEntity>urlEntityList, Context context, int totalWidth, UrlClickListener urlClickListener){
        this.urlEntityList = urlEntityList;
        this.context = context;
        marginLeft = CommonUtils.dip2px(context,R.dimen.small_margin_size);
        width = (totalWidth - marginLeft*4)/5;
        height = CommonUtils.dip2px(context,R.dimen.md_btn_height);
        this.urlClickListener = urlClickListener;
    }

    @Override
    public int getCount() {
        return urlEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return urlEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        if(position%5 != 0){
            layoutParams.leftMargin = marginLeft;
        }
        textView.setLayoutParams(layoutParams);
        textView.setText(urlEntityList.get(position).getLabel());
        textView.setBackgroundResource(R.drawable.sm_border_decoration);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(this);
        textView.setId(position);
        return textView;
    }

    @Override
    public void onClick(View v) {
        urlClickListener.onClickListener((TextView)v);
    }
}
