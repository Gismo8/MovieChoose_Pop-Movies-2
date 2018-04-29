package com.gismo.moviechoose.task;

import android.content.Context;
import android.os.AsyncTask;

import com.gismo.moviechoose.model.ReviewObject;
import com.gismo.moviechoose.model.VideoObject;
import com.gismo.moviechoose.util.JSONUtils;
import com.gismo.moviechoose.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by gismo on 2018. 03. 13..
 */

public class MovieReviewsAsyncTask extends AsyncTask<String, Void, List<ReviewObject>> {

    private Context context;
    private AsyncTaskCompleteListener<List<ReviewObject>> listener;

    public MovieReviewsAsyncTask(Context context, AsyncTaskCompleteListener< List<ReviewObject>> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<ReviewObject> doInBackground(String... strings) {

        URL url = NetworkUtils.buildUrlForMovieReviews(strings[0]);

        List<ReviewObject> newList;
        try {
            newList = JSONUtils.extractReviewListFromJson(NetworkUtils.getResponseFromHttpUrl(url));
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
    protected void onPostExecute(List<ReviewObject> reviewObjects) {
        super.onPostExecute(reviewObjects);
        listener.onTaskComplete(reviewObjects);
    }
}
