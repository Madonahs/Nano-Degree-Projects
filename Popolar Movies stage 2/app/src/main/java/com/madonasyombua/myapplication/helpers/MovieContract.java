package com.madonasyombua.myapplication.helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author madon
 */
public class MovieContract {

    //the content authority to create base of all URI's which app will use.
    public static final String Content_Authority = "com.madonasyombua.myapplication";

    public static final Uri Base_Content_Uri = Uri.parse("content://" + Content_Authority);

    public static final String Path_favourite_movie = "favourite_movie";

    /**
     * this is my inner class that defines the table contents
     */
    public static final class MovieListEntry implements BaseColumns{

        public  static  final Uri Content_URI =
                Base_Content_Uri.buildUpon().appendPath(Path_favourite_movie).build();

        public  static final String Content_Type =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/" + Content_Authority + "/" + Path_favourite_movie;


        public static final String Content_Item_Type =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +Content_Authority + "/" + Path_favourite_movie;

        public static  final String TABLE_NAME = "favorite_movie"; // table name
        public static final String COLUMN_MOVIE_ID ="movie_id"; //Movie id as returned by the api

        public static final String COLUMN_TITLE = "title";// column title
        public static final String COLUMN_POSTER_IMAGE = "poster_image"; //column poster image
        public static final String COLUMN_OVERVIEW = "overview"; //column overview
        public static final String COLUMN_AVERAGE_RATING = "average_rating";//column average rating
        public static final String COLUMN_RELEASE_DATE = "release_date";//column release date
        public static final String COLUMN_BACKDROP_IMAGE = "backdrop_image";//column backdrop image


        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(Content_URI,id);
        }
    }

}
