package com.gismo.moviechoose.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.gismo.moviechoose.R;
import com.gismo.moviechoose.adapter.ReviewAdapter;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.model.ReviewObject;
import com.gismo.moviechoose.model.VideoObject;
import com.gismo.moviechoose.task.AsyncTaskCompleteListener;
import com.gismo.moviechoose.task.MovieReviewsAsyncTask;
import com.gismo.moviechoose.task.MovieVideosAsyncTask;
import com.github.florent37.shapeofview.ShapeOfView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 03. 11..
 */

public class MovieDetailsActivity extends AppCompatActivity {

    protected MovieObject movieObject;

    @BindView (R.id.posterImage) KenBurnsView posterImage;
    @BindView (R.id.progressBar) ProgressBar progressBar;
    @BindView (R.id.shapeOfView) ShapeOfView shapeOfView;
    @BindView (R.id.titleView) TextView titleView;
    @BindView (R.id.releaseDate) TextView releaseDate;
    @BindView (R.id.averageVote) TextView averageVote;
    @BindView (R.id.plotView) TextView plotView;
    @BindView (R.id.reviewsRecyclerView) RecyclerView reviewsRecyclerView;

    ArrayList<VideoObject> videoObjects;
    ArrayList<ReviewObject> reviewObjects;
    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        movieObject = (MovieObject) getIntent().getSerializableExtra(MovieObject.class.getSimpleName());

        reviewAdapter = new ReviewAdapter(this);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewsRecyclerView.setAdapter(reviewAdapter);

        startAsyncTasks(String.valueOf(movieObject.getId()));

        bindActivity();
    }

    private void bindActivity() {
        bindPosterImage();
        titleView.setText(movieObject.getOriginalTitle());
        releaseDate.setText(getFormattedReleaseYear(movieObject.getReleaseDate()));
        averageVote.setText(movieObject.getVoteAverage());
        plotView.setText(movieObject.getOverview());
    }

    private String getFormattedReleaseYear(String releaseDate) {
        String formattedReleaseYear = null;
        String[] parts = releaseDate.split("-");
        formattedReleaseYear = "(" + parts[0] + ")";
        return formattedReleaseYear;
    }

    private void startAsyncTasks(String movieId) {
        progressBar.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(GONE);
        new MovieVideosAsyncTask(this, new FetchVideosDataTaskCompleteListener()).execute(movieId);
        new MovieReviewsAsyncTask(this, new FetchReviewDataTaskCompleteListener()).execute(movieId);
    }

    private void bindPosterImage() {
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.with(MovieDetailsActivity.this)
                        .load(movieObject.getImageUrl(false))
                        .centerCrop()
                        .resize(metrics.widthPixels, metrics.heightPixels / 2)
                        .into(posterImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
        }, 800);
    }

    public class FetchVideosDataTaskCompleteListener implements AsyncTaskCompleteListener<List<VideoObject>> {
        @Override
        public void onTaskComplete(List<VideoObject> result) {
            videoObjects = (ArrayList<VideoObject>) result;
            videoObjects.toString();
            //notifyAdapterWithMovies(movieObjectList);
        }
    }

    public class FetchReviewDataTaskCompleteListener implements AsyncTaskCompleteListener<List<ReviewObject>> {
        @Override
        public void onTaskComplete(List<ReviewObject> result) {
            reviewObjects = (ArrayList<ReviewObject>) result;
            reviewObjects.toString();
            reviewAdapter.setReviewObjects(reviewObjects);
            reviewAdapter.notifyDataSetChanged();
        }
    }
}
