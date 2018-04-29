package com.gismo.moviechoose.task;

import android.content.Context;
import android.os.AsyncTask;

import com.gismo.moviechoose.enums.SortingOptions;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.util.JSONUtils;
import com.gismo.moviechoose.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by gismo on 2018. 03. 13..
 */

public class MoviesAsyncTask extends AsyncTask<SortingOptions, Void, List<MovieObject>> {


    private Context context;
    private AsyncTaskCompleteListener<List<MovieObject>> listener;

    public MoviesAsyncTask(Context ctx, AsyncTaskCompleteListener< List<MovieObject>> listener) {
        this.context = ctx;
        this.listener = listener;
    }

    @Override
    protected List<MovieObject> doInBackground(SortingOptions... sortingOption) {
        URL url = NetworkUtils.buildUrlForMovieList(sortingOption[0]);

        List<MovieObject> newList;
        try {
            newList = JSONUtils.extractMovieObjectFromJson(NetworkUtils.getResponseFromHttpUrl(url));
            return newList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<MovieObject> movieObjects) {
        super.onPostExecute(movieObjects);
        listener.onTaskComplete(movieObjects);
    }
}
