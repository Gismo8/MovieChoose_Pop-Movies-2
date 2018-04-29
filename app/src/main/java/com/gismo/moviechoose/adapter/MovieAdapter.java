package com.gismo.moviechoose.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2018. 02. 24..
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{

    private List<MovieObject> movieList = new ArrayList<>();
    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public MovieObject getItem(int position) {
        return movieList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView moviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onListItemClick(adapterPosition);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.movie_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieObject movie = movieList.get(position);
        Picasso.with(holder.itemView.getContext())
                .setLoggingEnabled(true);

            Picasso.with(holder.itemView.getContext())
                    .load(movie.getImageUrl(true))
                    .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }

    public void setMovieData(List<MovieObject> movieData) {
        movieList = movieData;
        notifyDataSetChanged();
    }

}
