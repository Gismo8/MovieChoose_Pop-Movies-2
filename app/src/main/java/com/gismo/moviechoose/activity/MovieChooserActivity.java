package com.gismo.moviechoose.activity;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.gismo.moviechoose.task.AsyncTaskCompleteListener;
import com.gismo.moviechoose.task.MoviesAsyncTask;
import com.gismo.moviechoose.util.JSONUtils;
import com.gismo.moviechoose.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static android.view.View.GONE;

public class MovieChooserActivity extends AppCompatActivity {

    protected List<MovieObject> movieObjectList;
    protected MovieAdapter adapter;
    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;
    protected int numberOfColumns = 3;
    protected SortingOptions sortingOption = SortingOptions.TOP_RATED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_chooser);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setUpAdapter();
        setUpRecyclerView();

        startAsyncTask(SortingOptions.MOST_POPULAR);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setUpAdapter() {
        adapter = new MovieAdapter(new MovieAdapter.MovieAdapterOnCLickHandler() {
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAsyncTask(SortingOptions sortingOption) {
        this.sortingOption = sortingOption;
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
        new MoviesAsyncTask(this, new FetchMyDataTaskCompleteListener()).execute(sortingOption);
    }

    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<List<MovieObject>> {
        @Override
        public void onTaskComplete(List<MovieObject> result) {
            recyclerView.setVisibility(View.VISIBLE);
            movieObjectList = result;
            adapter.setMovieData(movieObjectList);
            progressBar.setVisibility(GONE);
            adapter.notifyDataSetChanged();
        }
    }
}
