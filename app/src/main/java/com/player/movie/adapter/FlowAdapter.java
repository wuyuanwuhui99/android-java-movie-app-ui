package com.player.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.player.movie.R;
import com.player.movie.entity.MovieUrlEntity;
import com.player.movie.view.FlowLayout;

import java.util.List;

public class FlowAdapter extends FlowLayout.Adapter<FlowAdapter.FlowViewHolder> {

    private static final String TAG = "FlowAdapter";

    private Context mContext;
    private List<MovieUrlEntity> movieUrlEntities;

    public FlowAdapter(Context mContext, List<MovieUrlEntity> movieUrlEntities) {
        this.mContext = mContext;
        this.movieUrlEntities = movieUrlEntities;
    }

    @Override
    public FlowViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.url_item, parent, false);
        return new FlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlowViewHolder holder, int position) {
        holder.tv.setText(movieUrlEntities.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return movieUrlEntities.size();
    }

    class FlowViewHolder extends FlowLayout.ViewHolder {
        TextView tv;

        public FlowViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.url_item);
        }
    }
}
