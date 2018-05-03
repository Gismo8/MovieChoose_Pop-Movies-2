package com.gismo.moviechoose.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.gismo.moviechoose.R;
import com.gismo.moviechoose.adapter.ReviewAdapter;
import com.gismo.moviechoose.adapter.VideoAdapter;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.model.ReviewObject;
import com.gismo.moviechoose.model.VideoObject;
import com.gismo.moviechoose.data.FavoriteMoviesOpenHelper;
import com.gismo.moviechoose.data.MoviesContract;
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

    private static final String APP_INTENT_STRING = "vnd.youtube:";
    private static final String WEB_INTENT_STRING = "http://www.youtube.com/watch?v=";

    protected MovieObject movieObject;

    @BindView(R.id.posterImage)
    KenBurnsView posterImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.shapeOfView)
    ShapeOfView shapeOfView;
    @BindView(R.id.titleView)
    TextView titleView;
    @BindView(R.id.releaseDate)
    TextView releaseDate;
    @BindView(R.id.averageVote)
    TextView averageVote;
    @BindView(R.id.plotView)
    TextView plotView;
    @BindView(R.id.reviewsRecyclerView)
    RecyclerView reviewsRecyclerView;
    @BindView(R.id.favoriteButton)
    FloatingActionButton favoriteButton;
    @BindView(R.id.videoRecyclerView)
    RecyclerView videoRecyclerView;
    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    ArrayList<VideoObject> videoObjects;
    ArrayList<ReviewObject> reviewObjects;
    ReviewAdapter reviewAdapter;
    VideoAdapter videoAdapter;
    boolean favoriteButtonClicked;
    protected SQLiteDatabase favMoviesDb;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        movieObject = (MovieObject) getIntent().getSerializableExtra(MovieObject.class.getSimpleName());

        reviewAdapter = new ReviewAdapter(this);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewsRecyclerView.setAdapter(reviewAdapter);

        videoAdapter = new VideoAdapter(this, new VideoAdapter.VideoAdapterOnClickHandler() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                watchYoutubeVideo(videoObjects.get(clickedItemIndex).getKey());
            }
        });
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setAdapter(videoAdapter);

        startAsyncTasks(String.valueOf(movieObject.getId()));

        FavoriteMoviesOpenHelper helper = new FavoriteMoviesOpenHelper(this);
        favMoviesDb = helper.getWritableDatabase();

        favoriteButtonClicked = !isMovieAlreadyFavorite(movieObject.getId());
        setupFloatingActionButton();

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
            videoAdapter.setVideoObjects(videoObjects);
            videoAdapter.notifyDataSetChanged();
        }
    }

    public class FetchReviewDataTaskCompleteListener implements AsyncTaskCompleteListener<List<ReviewObject>> {
        @Override
        public void onTaskComplete(List<ReviewObject> result) {
            reviewObjects = (ArrayList<ReviewObject>) result;
            if (reviewObjects.size() == 0) {
                reviewObjects.add(new ReviewObject("", getResources().getString(R.string.no_reviews)));
            }
            reviewAdapter.setReviewObjects(reviewObjects);
            reviewAdapter.notifyDataSetChanged();
        }
    }

    private void setupFloatingActionButton() {
        if (!favoriteButtonClicked) {
            favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonInactive)));
        } else {
            favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favoriteButtonClicked) {
                    favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    removeMovieToFavorites(movieObject);
                    Snackbar.make(root, getText(R.string.removed_from_favorites), 2000).show();
                    favoriteButtonClicked = true;
                } else {
                    favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonInactive)));
                    favoriteButtonClicked = false;
                    addMovieToFavorites(movieObject);
                    Snackbar.make(root, getText(R.string.added_to_favorites), 2000).show();
                }
            }
        });
    }

    private void addMovieToFavorites(MovieObject movieObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movieObject.getId());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, movieObject.getOriginalTitle());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, movieObject.getOverview());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, movieObject.getPosterPath());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movieObject.getReleaseDate());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieObject.getVoteAverage());
        getContentResolver().insert(MoviesContract.BASE_CONTENT_URI, contentValues);
    }

    private void removeMovieToFavorites(MovieObject movieObject) {
        getContentResolver().delete(MoviesContract.BASE_CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + "=" + String.valueOf(movieObject.getId()),
                null);
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_INTENT_STRING + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(WEB_INTENT_STRING + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    private boolean isMovieAlreadyFavorite(int id) {
        String Query = "Select * from " + MoviesContract.MovieEntry.TABLE_NAME + " where " + MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = " + id;
        Cursor cursor = favMoviesDb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
