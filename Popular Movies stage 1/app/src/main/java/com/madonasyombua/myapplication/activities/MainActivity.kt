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
package com.madonasyombua.myapplication.activities

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.madonasyombua.myapplication.BuildConfig
import com.madonasyombua.myapplication.R
import com.madonasyombua.myapplication.adapters.MovieAdapter
import com.madonasyombua.myapplication.interfaces.OnTaskCompleted
import com.madonasyombua.myapplication.model.Movie
import com.madonasyombua.myapplication.utils.MovieAsyncTask
import com.madonasyombua.myapplication.utils.TopRatedMovies
import java.util.*

/**
 * @author madon
 */
class MainActivity : AppCompatActivity() {
    @BindView(R.id.grid_view)
    var mGridView: GridView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        mGridView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val movie = parent.getItemAtPosition(position) as Movie
            val intent = Intent(applicationContext, MovieListDetail::class.java)
            intent.putExtra(resources.getString(R.string.movie_parcel), movie)
            startActivity(intent)
        }
        if (savedInstanceState == null) {
            getMovies(sortedMethod)
        } else {
            val parcelable = savedInstanceState.getParcelableArray(getString(R.string.movie_parcel))
            if (parcelable != null) {
                val numMovieObjects = parcelable.size
                val movies = arrayOfNulls<Movie>(numMovieObjects)
                for (i in 0 until numMovieObjects) {
                    movies[i] = parcelable[i] as Movie
                }
                mGridView?.adapter = MovieAdapter(this, movies)
            }
        }
    }

    /**
     * If a user sorts and sets
     * @return returns the sorted method from shared preference
     */
    private val sortedMethod: String?
        get() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            return sharedPreferences.getString(getString(R.string.pref_sort_method_key),
                    getString(R.string.tmdb_sort_pop_desc))
        }

    /**
     * in this method i will get the objects from gridview and save to bundle
     * @param outState save the objects to bundle
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val mGridViewCount = mGridView?.count
        if (mGridViewCount != null) {
            if (mGridViewCount > 0) {
                val movies = arrayOfNulls<Movie>(mGridViewCount)
                for (i in 0 until mGridViewCount) {
                    movies[i] = mGridView?.getItemAtPosition(i) as Movie
                }
                outState.putParcelableArray(getString(R.string.movie_parcel), movies)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.menu_sort_by_pop -> {
                setTitle(R.string.app_name)
                if (networkAvailable()) {

                    //Make sure you enter your key here
                    val apiKey = ""
                    val onTaskCompleted: OnTaskCompleted = object : OnTaskCompleted {
                        override fun onFetchMovie(movies: Array<Movie?>?) {
                            mGridView!!.adapter = MovieAdapter(applicationContext, movies)
                        }
                    }
                    val movieAsyncTask = MovieAsyncTask(onTaskCompleted, apiKey)
                    movieAsyncTask.execute(sortedMethod)
                } else {
                    Toast.makeText(this, getString(R.string.connect), Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.menu_sort_by_avg -> {
                setTitle(R.string.top_rated)
                if (networkAvailable()) {

                    //Make sure you enter your key here
                    val apiKey = ""
                    val onTaskCompleted: OnTaskCompleted = object : OnTaskCompleted {
                        override fun onFetchMovie(movies: Array<Movie?>?) {
                            mGridView?.adapter = movies?.let { MovieAdapter(applicationContext, it) }
                        }
                    }
                    val movieAsyncTask = TopRatedMovies(onTaskCompleted, apiKey)
                    movieAsyncTask.execute(sortedMethod)
                } else {
                    Toast.makeText(this, getString(R.string.connect), Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * When a user changes the sort criteria (“most popular and highest rated”)
     * the main view gets updated correctly.
     * @param sortMethod the TMDB api method for sorting the movies
     */
    private fun getMovies(sortMethod: String?) {
        if (networkAvailable()) {

            //Make sure you enter your key here
            val apiKey: String = BuildConfig.my_api
            val onTaskCompleted: OnTaskCompleted = object : OnTaskCompleted {
                override fun onFetchMovie(movies: Array<Movie?>?) {
                    mGridView?.adapter = movies?.let { MovieAdapter(applicationContext, it) }
                }
            }
            val movieAsyncTask = MovieAsyncTask(onTaskCompleted, apiKey)
            movieAsyncTask.execute(sortMethod)
        } else {
            Toast.makeText(this, getString(R.string.connect), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This method will check my internet connection
     * @return successful if connected to internet and false if not
     */
    private fun networkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activeNetworkInfo = Objects.requireNonNull(connectivityManager).activeNetworkInfo
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}