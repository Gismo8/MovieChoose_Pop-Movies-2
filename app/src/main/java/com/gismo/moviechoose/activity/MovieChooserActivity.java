package com.gismo.moviechoose.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gismo.moviechoose.adapter.MovieAdapter;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.R;
import com.gismo.moviechoose.enums.SortingOptions;
import com.gismo.moviechoose.sqlite.FavoriteMoviesOpenHelper;
import com.gismo.moviechoose.sqlite.MoviesContract;
import com.gismo.moviechoose.task.AsyncTaskCompleteListener;
import com.gismo.moviechoose.task.MoviesAsyncTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class MovieChooserActivity extends AppCompatActivity {

    protected List<MovieObject> movieObjectList;
    protected MovieAdapter adapter;
    protected int NUMBER_OF_COLUMNS = 3;
    protected SortingOptions sortingOption = SortingOptions.TOP_RATED;
    protected SQLiteDatabase favMoviesDb;
    protected Cursor cursor;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_chooser);
        ButterKnife.bind(this);

        setUpAdapter();
        setUpRecyclerView();

        FavoriteMoviesOpenHelper helper = new FavoriteMoviesOpenHelper(this);
        favMoviesDb = helper.getWritableDatabase();

        startAsyncTask(SortingOptions.MOST_POPULAR);
        cursor = getAllFavoriteMovies();
    }

    private Cursor getAllFavoriteMovies() {
        return favMoviesDb.query(
                MoviesContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE);
    }

    public ArrayList<MovieObject> createFavoriteMovieListFromCursor(Cursor cursor) {
        ArrayList<MovieObject> favoriteMovies = new ArrayList<MovieObject>();
        while(cursor.moveToNext()) {
           /* MovieObject movieObject = new MovieObject(
                    cursor.getString(cursor.getColumnIndex(favMoviesDb.)
            )

            favoriteMovies.add(cursor.getString(mCursor.getColumnIndex(dbAdapter.KEY_NAME))); //add the item*/
        }
        return favoriteMovies;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setUpAdapter() {
        adapter = new MovieAdapter(new MovieAdapter.MovieAdapterOnClickHandler() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                Intent activityIntent = new Intent(MovieChooserActivity.this, MovieDetailsActivity.class);
                activityIntent.putExtra(MovieObject.class.getSimpleName(), adapter.getItem(clickedItemIndex));
                startActivity(activityIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sorting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_pop:
                startAsyncTask(SortingOptions.MOST_POPULAR);
                break;
            case R.id.top_rated:
                startAsyncTask(SortingOptions.TOP_RATED);
                break;
            case R.id.favorites:
                showFavoriteMovies();
                Toast.makeText(this, "favorites selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAsyncTask(SortingOptions sortingOption) {
        this.sortingOption = sortingOption;
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
        new MoviesAsyncTask(this, new FetchMyMovieDataTaskCompleteListener()).execute(sortingOption);
    }

    public class FetchMyMovieDataTaskCompleteListener implements AsyncTaskCompleteListener<List<MovieObject>> {
        @Override
        public void onTaskComplete(List<MovieObject> result) {
            movieObjectList = result;
            notifyAdapterWithMovies(movieObjectList);
        }
    }

    private void showFavoriteMovies() {
        notifyAdapterWithMovies(movieObjectList);
    }

    private void notifyAdapterWithMovies(List<MovieObject> movieObjectList) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setMovieData(movieObjectList);
        progressBar.setVisibility(GONE);
        adapter.notifyDataSetChanged();
    }

}
