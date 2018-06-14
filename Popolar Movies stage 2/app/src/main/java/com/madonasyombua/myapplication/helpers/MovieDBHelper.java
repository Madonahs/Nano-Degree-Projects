package com.madonasyombua.myapplication.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author madon
 */
class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * this is my sqlitedatabase
     * @param db the created table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieListEntry.TABLE_NAME + " (" +
                MovieContract.MovieListEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.MovieListEntry.COLUMN_ID + " TEXT NOT NULL, " +
                MovieContract.MovieListEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_RUNTIME + " INTEGER NOT NULL," +
                MovieContract.MovieListEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL)" +
                "; ";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieListEntry.TABLE_NAME);
        onCreate(db);
    }

}
