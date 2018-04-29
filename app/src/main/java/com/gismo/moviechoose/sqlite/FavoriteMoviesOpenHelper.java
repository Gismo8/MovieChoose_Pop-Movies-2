package com.gismo.moviechoose.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.gismo.moviechoose.sqlite.MoviesContract.*;

/**
 * Created by gismo on 2018. 04. 02..
 */

public class FavoriteMoviesOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritemovies.db";
    private static final int DATABASE_VERSION = 1;

    public  FavoriteMoviesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_FAVORITE_MOVIES_DATABASE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(CREATE_FAVORITE_MOVIES_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
