package com.gismo.moviechoose.util;

import android.graphics.Movie;
import android.text.TextUtils;
import android.util.Log;

import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.model.ReviewObject;
import com.gismo.moviechoose.model.VideoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2018. 03. 09..
 */

public class JSONUtils {

    //KEYS for MovieObjects
    public static final String RESULTS = "results";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_PATH = "poster_path";
    public static final String RELEASE_DATE = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String OVERVIEW = "overview";

    //KEYS for VideoObjects
    public static final String SITE = "site";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String KEY = "key";
    public static final String SIZE = "size";

    //KEYS for ReviewObjects
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";


    public static List<MovieObject> extractMovieObjectFromJson(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        List<MovieObject> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray resultsArray = baseJsonResponse.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject results = resultsArray.getJSONObject(i);

                String originalTitle = "";
                if (results.has(ORIGINAL_TITLE)) {
                    originalTitle = results.optString(ORIGINAL_TITLE);
                }

                String posterPath = "";
                if (results.has(POSTER_PATH)) {
                    posterPath = results.optString(POSTER_PATH);
                }

                String releaseDate = "";
                if (results.has(RELEASE_DATE)) {
                    releaseDate = results.optString(RELEASE_DATE);
                }

                String voteAverage = "";
                if (results.has(VOTE_AVERAGE)) {
                    voteAverage = results.optString(VOTE_AVERAGE);
                }

                String overview = "";
                if (results.has(OVERVIEW)) {
                    overview = results.optString(OVERVIEW);
                }

                int id = 0;
                if (results.has(ID)) {
                    id = results.optInt(ID);
                }

                MovieObject movie = new MovieObject(originalTitle, posterPath, releaseDate, voteAverage, overview, id);
                movies.add(movie);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of movies
        return movies;
    }

    public static List<ReviewObject> extractReviewListFromJson(String videoJSON) {

        if (TextUtils.isEmpty(videoJSON)) {
            return null;
        }
        List<ReviewObject> reviews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(videoJSON);
            JSONArray resultsArray = baseJsonResponse.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject results = resultsArray.getJSONObject(i);

                String author = "";
                if (results.has(AUTHOR)) {
                    author = results.optString(AUTHOR);
                }

                String content = "";
                if (results.has(CONTENT)) {
                    content = results.optString(CONTENT);
                }

                ReviewObject review = new ReviewObject(author, content);
                reviews.add(review);

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of movies
        return reviews;
    }

    public static List<VideoObject> extractVideoListFromJson(String reviewJSON) {

        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }
        List<VideoObject> videos = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(reviewJSON);
            JSONArray resultsArray = baseJsonResponse.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject results = resultsArray.getJSONObject(i);

                String name = "";
                if (results.has(NAME)) {
                    name = results.optString(NAME);
                }

                String key = "";
                if (results.has(KEY)) {
                    key = results.optString(KEY);
                }

                VideoObject movie = new VideoObject(name, key);
                videos.add(movie);

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of movies
        return videos;
    }
}
