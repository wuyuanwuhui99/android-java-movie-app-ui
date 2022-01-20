package com.player.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.player.movie.R;
import com.player.movie.entity.MovieUrlEntity;

import java.util.List;

public class PlayUrlPannelAdapter extends RecyclerView.Adapter<PlayUrlPannelAdapter.ViewHolder> {
    private List<MovieUrlEntity>  movieUrlList;
    private Context context;

    public PlayUrlPannelAdapter(Context context, List<MovieUrlEntity> movieUrlList) {
        this.movieUrlList = movieUrlList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayUrlPannelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new PlayUrlPannelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayUrlPannelAdapter.ViewHolder holder, int position) {
        holder.textView.setText(movieUrlList.get(position).getLabel());
        holder.itemView.setOnClickListener(view -> { // 列表绑定点击事件

        });
    }

    @Override
    public int getItemCount() {
        return movieUrlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.url_pannel_item);
        }
    }
}
