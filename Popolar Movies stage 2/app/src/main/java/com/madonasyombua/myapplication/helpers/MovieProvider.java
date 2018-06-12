package com.madonasyombua.myapplication.helpers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.madonasyombua.myapplication.R;


import java.util.Objects;

/**
 * @author madon
 */

public class MovieProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();//uri Matcher used by the content provider

    private MovieDBHelper movieDBHelper;

    static final int FAv_Movies = 100;
    static final int Fav_Movie_item = 101;

    /**
     *
     * @return matcher
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher((UriMatcher.NO_MATCH));
        final String authority = MovieContract.Content_Authority;

        matcher.addURI(authority,MovieContract.Path_favourite_movie,FAv_Movies);
        matcher.addURI(authority,MovieContract.Path_favourite_movie,Fav_Movie_item);

        return matcher;
    }


    /**
     * Get the movie Db Helper
     * @return true
     */

    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    /**
     * Here i will create switch statements that, given a URI will describe what kind
     * <p/>
     * of request it is and query the database
     * @param uri the uri
     * @param projection the string projection
     * @param selection String selection
     * @param selectionArgs args
     * @param sortOrder the order
     * @return cursor
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {

            case FAv_Movies: {
                cursor = movieDBHelper.getReadableDatabase()
                        .query(MovieContract.MovieListEntry.TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException(String.valueOf(R.string.uri_error) + uri);
        }
        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final  int match = sUriMatcher.match(uri);

        switch (match){
            case FAv_Movies:
                return  MovieContract.MovieListEntry.Content_Type;

            case Fav_Movie_item:
                return MovieContract.MovieListEntry.Content_Item_Type;

            default:
                throw  new UnsupportedOperationException(String.valueOf(R.string.uri_error) + uri);
        }

    }

    /**
     *
     * @param uri the uri
     * @param values content values
     * @return uri
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase database = movieDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case FAv_Movies:{
                long id = database.insert(MovieContract.MovieListEntry.TABLE_NAME,null,values);

                if(id > 0)
                    returnUri = MovieContract.MovieListEntry.buildMovieUri(id);
                else
                    throw new SQLException("Failed to insert new Row" + uri);

                break;
            }
            default:
                throw  new UnsupportedOperationException(String.valueOf(R.string.uri_error) + uri);
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    /**
     * A null value will delete all rows i am checking if the row deleted != 0
     * @param uri uri
     * @param selection String selection
     * @param selectionArgs arguments
     * @return the rows to be deleted
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = movieDBHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(null == selection) selection = "1";
        switch (match){
            case FAv_Movies:
                rowsDeleted = database.delete(MovieContract.MovieListEntry.TABLE_NAME,selection,selectionArgs);
                break;
                default:
                    throw new UnsupportedOperationException(String.valueOf(R.string.uri_error)+ uri);

        }
       if(rowsDeleted != 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
       }

        return rowsDeleted; //the actual rows deleted
    }

    /**
     *
     * @param uri the uri
     * @param values values
     * @param selection string selection
     * @param selectionArgs selection args
     * @return updated rows
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = movieDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsUpated;

        switch (match){
            case FAv_Movies:
            rowsUpated = database.update(MovieContract.MovieListEntry.TABLE_NAME,values,selection,selectionArgs);

            break;
            default:
                throw new UnsupportedOperationException(String.valueOf(R.string.uri_error )+ uri);

        }
        if(rowsUpated != 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }

        return rowsUpated;
    }
}
