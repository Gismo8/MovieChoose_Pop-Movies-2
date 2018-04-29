package com.gismo.moviechoose.model;

import android.net.Uri;

import com.gismo.moviechoose.util.NetworkUtils;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by test on 2018. 02. 24..
 */

public class MovieObject implements Serializable {

    String originalTitle;
    String posterPath;
    String releaseDate;
    String voteAverage;
    String overview;
    int id;

    public MovieObject(String originalTitle, String posterPath, String releaseDate, String voteAverage, String overview, int id) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.id = id;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getImageUrl(boolean isThumbnail) {
        String url = NetworkUtils.MOVIE_PICTURE_URL + (isThumbnail ? NetworkUtils.MOVIE_PICTURE_THUMBNAIL : NetworkUtils.MOVIE_PICTURE_BIG) + getPosterPath();
        return url;
    }

    public int getId() {
        return id;
    }
}
