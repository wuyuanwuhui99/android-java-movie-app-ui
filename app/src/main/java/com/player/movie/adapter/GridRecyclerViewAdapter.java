package com.player.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.R;
import com.player.movie.activity.MovieDetailActivity;
import com.player.movie.api.Api;
import com.player.movie.entity.MovieEntity;
import com.player.movie.utils.CommonUtils;

import java.util.List;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private List<MovieEntity>movieEntityList;
    private Context context;
    private int itemWidth;
    LinearLayout.LayoutParams imgLayoutParams;
    public GridRecyclerViewAdapter(List<MovieEntity> movieEntityList, Context context,int rowWidth){
        this.movieEntityList = movieEntityList;
        this.context = context;
        itemWidth = (rowWidth - CommonUtils.dip2px(context,R.dimen.container_padding_size) * 2)/3;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        imgLayoutParams = new LinearLayout.LayoutParams(itemWidth, (int) (itemWidth/0.75));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == 0){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 0;
            holder.itemView.setLayoutParams(layoutParams);
        }
        String path0 = Api.HOST + movieEntityList.get(position * 3).getLocalImg();
        Glide.with(context).load(path0).into(holder.imageView0);
        holder.imageView0.setLayoutParams(imgLayoutParams);
        holder.textView0.setText(movieEntityList.get(position).getMovieName());
        holder.itemView0.setTag(movieEntityList.get(position));
        holder.itemView0.setOnClickListener(this);


        if(position * 3 + 1 < movieEntityList.size()){
            holder.imageView1.setLayoutParams(imgLayoutParams);
            String path1 = Api.HOST + movieEntityList.get(position * 3 + 1).getLocalImg();
            Glide.with(context).load(path1).into(holder.imageView1);
            holder.textView1.setText(movieEntityList.get(position * 3 + 1).getMovieName());
            holder.itemView1.setTag(movieEntityList.get(position * 3 + 1));
            holder.itemView1.setOnClickListener(this);
        }else{
            holder.itemView1.setVisibility(View.INVISIBLE);
        }

        if(position * 3 + 2 < movieEntityList.size()){
            holder.imageView2.setLayoutParams(imgLayoutParams);
            String path2 = Api.HOST + movieEntityList.get(position * 3 + 2).getLocalImg();
            Glide.with(context).load(path2).into(holder.imageView2);
            holder.textView2.setText(movieEntityList.get(position * 3 + 2).getMovieName());
            holder.itemView2.setTag(movieEntityList.get(position * 3 + 2));
            holder.itemView2.setOnClickListener(this);
        }else{
            holder.itemView2.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return (int) Math.ceil(movieEntityList.size() / 3);
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
        public final LinearLayout itemView0;
        public final RoundedImageView imageView0;
        public final TextView textView0;

        public final LinearLayout itemView1;
        public final RoundedImageView imageView1;
        public final TextView textView1;

        public final LinearLayout itemView2;
        public final RoundedImageView imageView2;
        public final TextView textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView0 = itemView.findViewById(R.id.movie_item_0);
            imageView0 = itemView.findViewById(R.id.movie_img_0);
            textView0 = itemView.findViewById(R.id.movie_name_0);

            itemView1 = itemView.findViewById(R.id.movie_item_1);
            imageView1 = itemView.findViewById(R.id.movie_img_1);
            textView1 = itemView.findViewById(R.id.movie_name_1);

            itemView2 = itemView.findViewById(R.id.movie_item_2);
            imageView2 = itemView.findViewById(R.id.movie_img_2);
            textView2 = itemView.findViewById(R.id.movie_name_2);
        }
    }
}
