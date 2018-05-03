package com.gismo.moviechoose.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gismo on 2018. 04. 02..
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.gismo.moviechoose";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();
        public static final String CONTENT =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildFavoriteMoviesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
