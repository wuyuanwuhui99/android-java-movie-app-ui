package com.player.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.R;
import com.player.common.Constants;
import com.player.movie.activity.MovieDetailActivity;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private List<MovieEntity>movieEntityList;
    private Context context;
    public CategoryRecyclerViewAdapter(List<MovieEntity> movieEntityList,Context context){
        this.movieEntityList = movieEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = Constants.HOST + movieEntityList.get(position).getLocalImg();
        Glide.with(context).load(path).into(holder.imageView);
        holder.textView.setText(movieEntityList.get(position).getMovieName());
        holder.itemView.setTag(movieEntityList.get(position));
        if(position == movieEntityList.size() - 1){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,0);//4个参数按顺序分别是左上右下
            holder.itemView.setLayoutParams(layoutParams);
        }
        holder.itemView.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return movieEntityList.size();
    }

    // 列表绑定点击事件
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        String movieJSONString = JSON.toJSONString(v.getTag());
        intent.putExtra("movieItem",movieJSONString);
        context.startActivity(intent);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public final RoundedImageView imageView;
        public final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_img);
            textView = itemView.findViewById(R.id.movie_name);
        }
    }
}
