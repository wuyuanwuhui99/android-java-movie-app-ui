package com.player.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private List<MovieEntity>movieEntityList;
    private Context context;
    public SearchRecyclerViewAdapter(List<MovieEntity> movieEntityList, Context context){
        this.movieEntityList = movieEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = Constants.HOST + movieEntityList.get(position).getLocalImg();
        Glide.with(context).load(path).into(holder.movieImg);
        holder.movieName.setText(movieEntityList.get(position).getMovieName());
        if(movieEntityList.get(position).getStar() != null){
            holder.star.setText("主演：" + movieEntityList.get(position).getStar());
        }else {
            holder.star.setText("主演：暂无数据");
        }
        if(movieEntityList.get(position).getDirector() != null){
            holder.director.setText("导演：" + movieEntityList.get(position).getDirector());
        }else {
            holder.director.setText("导演：暂无数据");
        }
        if(movieEntityList.get(position).getType() != null){
            holder.type.setText("类型：" + movieEntityList.get(position).getType());
        }else {
            holder.type.setText("类型：暂无数据");
        }
        if(movieEntityList.get(position).getReleaseTime() != null){
            holder.releaseTime.setText("上映时间：" + movieEntityList.get(position).getReleaseTime());
        }else {
            holder.releaseTime.setText("上映时间：暂无数据");
        }
        holder.itemView.setTag(movieEntityList.get(position));
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
        public final RoundedImageView movieImg;
        public final TextView movieName;
        public final TextView star;
        public final TextView director;
        public final TextView type;
        public final TextView releaseTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.search_movie_img);
            movieName = itemView.findViewById(R.id.search_movie_name);
            star = itemView.findViewById(R.id.search_star);
            director = itemView.findViewById(R.id.search_director);
            type = itemView.findViewById(R.id.search_type);
            releaseTime = itemView.findViewById(R.id.search_release_time);
        }
    }
}
