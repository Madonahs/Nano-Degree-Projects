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
package com.madonasyombua.myapplication.helpers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author madona syombua
 *
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
