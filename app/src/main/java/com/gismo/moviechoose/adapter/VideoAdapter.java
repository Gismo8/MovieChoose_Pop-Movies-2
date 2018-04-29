package com.gismo.moviechoose.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gismo.moviechoose.R;
import com.gismo.moviechoose.model.VideoObject;
import com.gismo.moviechoose.util.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2018. 02. 24..
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    private List<VideoObject> videoObjects = new ArrayList<>();
    private Context context;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public VideoObject getItem(int position) {
        return videoObjects.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView thumbnail;
        public final ImageView playButton;
        public final RelativeLayout root;
        public final ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbNail);
            root = (RelativeLayout) itemView.getRootView();
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            playButton = (ImageView) itemView.findViewById(R.id.playButton);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.view_trailer_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VideoObject video = videoObjects.get(position);
        Picasso.with(context).load(String.valueOf(NetworkUtils.buildUrlForYoutubeThumbnail(video.getKey())))
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.playButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (videoObjects == null) return 0;
        return videoObjects.size();
    }

    public void setVideoObjects(List<VideoObject> videoObjects) {
        this.videoObjects = videoObjects;
        notifyDataSetChanged();
    }

}
