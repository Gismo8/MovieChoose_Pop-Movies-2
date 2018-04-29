package com.gismo.moviechoose.task;

import android.content.Context;
import android.os.AsyncTask;

import com.gismo.moviechoose.enums.SortingOptions;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.model.VideoObject;
import com.gismo.moviechoose.util.JSONUtils;
import com.gismo.moviechoose.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by gismo on 2018. 03. 13..
 */

public class MovieVideosAsyncTask extends AsyncTask<String, Void, List<VideoObject>> {

    private Context context;
    private AsyncTaskCompleteListener<List<VideoObject>> listener;

    public MovieVideosAsyncTask(Context context, AsyncTaskCompleteListener< List<VideoObject>> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<VideoObject> doInBackground(String... strings) {

        URL url = NetworkUtils.buildUrlForMovieVideos(strings[0]);

        List<VideoObject> newList;
        try {
            newList = JSONUtils.extractVideoListFromJson(NetworkUtils.getResponseFromHttpUrl(url));
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
    protected void onPostExecute(List<VideoObject> videoObjects) {
        super.onPostExecute(videoObjects);
        listener.onTaskComplete(videoObjects);
    }
}
