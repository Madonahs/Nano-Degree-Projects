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
package com.madonasyombua.myapplication.utils

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.madonasyombua.myapplication.BuildConfig
import com.madonasyombua.myapplication.R
import com.madonasyombua.myapplication.interfaces.OnTaskCompleted
import com.madonasyombua.myapplication.model.Movie
import com.squareup.picasso.BuildConfig
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

open class TopRatedMovies
/**
 * this is my Constructor which takes in two args
 * @param taskCompleted this is the interface listener
 * @param api_key this is the TMDB Api Key
 */(private val onTaskCompleted: OnTaskCompleted,
    /**
     * The Api key should be private and never to be shared.
     * To fetch popular movies, you will use the API from the movie db.org.
     *
     *
     * If you don’t already have an account, you will need to create
     * one in order to request an API Key.
     */
    private val my_api_key: String) : AsyncTask<String?, Void?, Array<Movie?>?>() {
    private val TAG = TopRatedMovies::class.java.name
    private var movieJson: String? = null

    /**
     * Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
     * original title
     * movie poster image thumbnail
     *
     *
     * A plot synopsis (called overview in the api)
     * user rating (called vote_average in the api)
     *
     *
     * release date
     * This method gets the movies from the Json data
     * @param movieJson the json string to be used
     * @return a list of movie objects
     * @throws JSONException jsonException
     */
    @Throws(JSONException::class)
    private fun getMovieFromJson(movieJson: String?): Array<Movie?> {
        val results = "results"
        val tagOriginalTitle = "original_title"
        val poster = "poster_path"
        val overview = "overview"
        val vote = "vote_average"
        val releaseDate = "release_date"

        // Get the array containing hte movies found
        val moviesJson = JSONObject(movieJson)
        val resultsArray = moviesJson.getJSONArray(results)

        // Create array of Movie objects that stores data from the JSON string
        val movies = arrayOfNulls<Movie>(resultsArray.length())

        // Traverse through movies one by one and get data
        for (i in 0 until resultsArray.length()) {
            // Initialize each object before it can be used
            movies[i] = Movie()

            // Object contains all tags we're looking for
            val movieInfo = resultsArray.getJSONObject(i)
            movies[i]?.setMovieOriginalTitle(movieInfo.getString(tagOriginalTitle))
            movies[i]?.moviePosterPath = movieInfo.getString(poster)
            movies[i]?.movieOverview = movieInfo.getString(overview)
            movies[i]?.movieVote = movieInfo.getDouble(vote)
            movies[i]?.setMovieRealease(movieInfo.getString(releaseDate))
        }
        return movies
    }

    /**
     * I will create a method getMovieApi to help me access the api
     * @param strings to be used in the call
     * @return the Url(uri. to string)
     * @throws MalformedURLException  MalformedURLException
     */
    @Throws(MalformedURLException::class)
    private fun getMovieApi(strings: Array<String>): URL {
        val baseUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key="
        val sort = "sort by"
        val apiKey: String = BuildConfig.my_api
        val uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(sort, strings[0])
                .appendQueryParameter(apiKey, my_api_key)
                .build()
        return URL(uri.toString())
    }

    /**
     *
     * @param movies this will notify the UI
     */
    override fun onPostExecute(movies: Array<Movie?>?) {
        super.onPostExecute(movies)
        onTaskCompleted.onFetchMovie(movies)
    }

    override fun doInBackground(vararg p0: String?): Array<Movie?>? {
        var httpURLConnection: HttpURLConnection? = null //connectivity
        var reader: BufferedReader? = null

        //I need to start fetching data from the api
        try {
            val url = getMovieApi(p0 as Array<String>)
            //getting the connection
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()
            val inputStream = httpURLConnection.inputStream
            val stringBuilder = StringBuilder()
            if (inputStream == null) { //if the input is null return null
                return null
            }
            reader = BufferedReader(InputStreamReader(inputStream))
            var addLine: String?
            while (reader.readLine().also { addLine = it } != null) {
                stringBuilder.append(addLine).append("\n") //append a single line
            }

            //checking is it is = 0
            if (stringBuilder.isEmpty()) {
                return null
            }
            movieJson = stringBuilder.toString()
        } catch (e: IOException) {
            Log.e(TAG, R.string.error.toString(), e)
        } finally {
            httpURLConnection?.disconnect()
            //close the reader
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e(TAG, R.string.error_while_closing.toString(), e)
                }
                try {
                    return getMovieFromJson(movieJson)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}