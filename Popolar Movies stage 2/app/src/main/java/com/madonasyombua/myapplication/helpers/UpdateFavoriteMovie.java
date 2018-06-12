package com.madonasyombua.myapplication.helpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.madonasyombua.myapplication.interfaces.DBUpdateListener;
import com.madonasyombua.myapplication.model.Movie;

/**
 * @author madon
 */
public class UpdateFavoriteMovie extends AsyncTask<Void,Void,Void> {

    private static final String TAG = UpdateFavoriteMovie.class.getSimpleName();
    private Context mContext;
    private Movie mMovie;
    private DBUpdateListener dbUpdateListener;

    public static final int Added_To_Favorite = 1;
    public static  final int Removed_from_Favorite = 2;

    //constructor
    public UpdateFavoriteMovie(Context context, Movie movie,DBUpdateListener updateListener ){
        this.mContext = context;
        this.mMovie = movie;
        this.dbUpdateListener = updateListener;

    }

    /**
     * Inside the do in Background i will create a method delete or save movies
     * I will first check if the movie id exists in the db
     * @param voids args
     * @return null
     */
    @Override
    protected Void doInBackground(Void... voids) {
        deleteOrSaveFavoriteMovie();
        return null;
    }

    private void deleteOrSaveFavoriteMovie() {
        Log.d(TAG,MovieContract.MovieListEntry.Content_URI.getAuthority());

        Cursor favMovieCursor = mContext.getContentResolver().query(
                MovieContract.MovieListEntry.Content_URI, new String []{
                        MovieContract.MovieListEntry.COLUMN_MOVIE_ID},
                MovieContract.MovieListEntry.COLUMN_MOVIE_ID+ "= ?",
                new String[]{String.valueOf(mMovie.getId())},null);

        if(favMovieCursor.moveToFirst()){

            int rowDeleted = mContext.getContentResolver().delete
                    (MovieContract.MovieListEntry.Content_URI,
                            MovieContract.MovieListEntry.COLUMN_MOVIE_ID + " = ?",
                            new String[]{String.valueOf(mMovie.getId())});

            if(rowDeleted > 0){
                dbUpdateListener.onSuccess(Removed_from_Favorite);
            }else{
                dbUpdateListener.onFailure();
            }
        }else {

            //Then add the data, along with the corresponding name of the data type,
            //so the content provider knows what kind of value is being inserted.
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieListEntry.COLUMN_MOVIE_ID,mMovie.getId());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_TITLE,mMovie.getTitle());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_POSTER_IMAGE,mMovie.getMoviePosterPath());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_OVERVIEW,mMovie.getMovieOverview());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_AVERAGE_RATING, mMovie.getVoteAverage());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_RELEASE_DATE,mMovie.getMovieRelease());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_BACKDROP_IMAGE, mMovie.getBackdropPath());


            Uri insertedUti = mContext.getContentResolver().insert(MovieContract.MovieListEntry.Content_URI,contentValues);


            long movieRowId = ContentUris.parseId(insertedUti);

            if(movieRowId > 0){

                dbUpdateListener.onSuccess(Added_To_Favorite);

            }else {
                dbUpdateListener.onFailure();
            }

            favMovieCursor.close();
        }


        //FIXME from here tomorrow the 10th of june
    }
}
