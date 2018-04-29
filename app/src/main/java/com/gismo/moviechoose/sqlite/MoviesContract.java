package com.gismo.moviechoose.sqlite;

import android.provider.BaseColumns;

/**
 * Created by gismo on 2018. 04. 02..
 */

public class MoviesContract {

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_MOVIE_ID = "movieId";

    }

}
