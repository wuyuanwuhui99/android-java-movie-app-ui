package com.player.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.BaseApplication;
import com.player.movie.R;
import com.player.movie.activity.MainActivity;
import com.player.movie.activity.MovieDetailActivity;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>{

    private List<MovieEntity>movieEntityList;

    public CategoryRecyclerViewAdapter(List<MovieEntity> movieEntityList){
        this.movieEntityList = movieEntityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path = Api.HOST + movieEntityList.get(position).getLocalImg();
        Glide.with(BaseApplication.getContext()).load(path).into(holder.imageView);
        holder.textView.setText(movieEntityList.get(position).getMovieName());
        if(position == movieEntityList.size() - 1){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,0);//4个参数按顺序分别是左上右下
            holder.itemView.setLayoutParams(layoutParams);
        }
        holder.itemView.setOnClickListener(view -> { // 列表绑定点击事件
            Context context = BaseApplication.getContext();
            Intent intent = new Intent(context, MovieDetailActivity.class);
            String movieJSONString = JSON.toJSONString(movieEntityList.get(position));
            intent.putExtra("movieItem",movieJSONString);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieEntityList.size();
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
