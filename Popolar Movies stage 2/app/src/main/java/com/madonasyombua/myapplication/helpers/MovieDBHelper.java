package com.madonasyombua.myapplication.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author madon
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int VERSION_NAME = 1;

    //constructor which take a parameter context
    public MovieDBHelper(Context context){

        super(context,DATABASE_NAME,null,VERSION_NAME);
    }

    /**
     * I have created a table to hold favorite movies if a user selects
     * @param db this is my Sql database
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_Fav_List = "CREATE TABLE " + MovieContract.MovieListEntry.TABLE_NAME + " (" +

                MovieContract.MovieListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieListEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_POSTER_IMAGE + " TEXT NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_AVERAGE_RATING + " REAL NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_BACKDROP_IMAGE + " TEXT NOT NULL)";

        db.execSQL(SQL_Fav_List);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieListEntry.TABLE_NAME);
        onCreate(db);

    }
}
