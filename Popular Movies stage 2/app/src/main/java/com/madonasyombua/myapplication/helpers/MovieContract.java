package com.madonasyombua.myapplication.helpers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author madon
 */
public class MovieContract {

    //the content authority to create base of all URI's which app will use.
    public static final String CONTENT_AUTHORITY = "com.madonasyombua.myapplication";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieListEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_MOVIES)
                        .build();

        public static  final String TABLE_NAME = "favorite_movie"; // table name
        public static final String COLUMN_ID = "movie_id";// movie column id
        public static final String COLUMN_TITLE = "title";//movie column title
        public static final String COLUMN_OVERVIEW = "overview";//movie column overview
        public static final String COLUMN_POSTER_PATH = "poster_path";//movie column poster path
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";// movie column backdrop path
        public static final String COLUMN_RELEASE_DATE = "release_date";// movie column release date
        public static final String COLUMN_RUNTIME = "runtime";// movie column runtime
        public static final String COLUMN_VOTE_AVERAGE = "vote_average"; // movie column vote

    }

}
