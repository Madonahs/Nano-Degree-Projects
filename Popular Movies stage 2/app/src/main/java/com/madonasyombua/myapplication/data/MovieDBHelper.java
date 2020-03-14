/*
 * Copyright (C) 2018 Madonah Syombua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.madonasyombua.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @author madona syombua
 *
 */
class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
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
