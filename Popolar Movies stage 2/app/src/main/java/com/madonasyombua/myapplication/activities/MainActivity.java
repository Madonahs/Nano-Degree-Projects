package com.madonasyombua.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.adapters.MovieAdapter;
import com.madonasyombua.myapplication.interfaces.OnTaskCompleted;
import com.madonasyombua.myapplication.model.Movie;

import com.madonasyombua.myapplication.utils.MovieAsyncTask;
import com.madonasyombua.myapplication.utils.TopRatedMovies;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author madon
 *
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.grid_view)
    GridView mGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), MovieListDetail.class);
                intent.putExtra(getResources().getString(R.string.movie_parcel), movie);

                startActivity(intent);
            }
        });

        if(savedInstanceState == null){
            getMovies(getSortedMethod());
        }else {

            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.movie_parcel));

            if (parcelable != null) {
                int numMovieObjects = parcelable.length;
                Movie[] movies = new Movie[numMovieObjects];
                for (int i = 0; i < numMovieObjects; i++) {
                    movies[i] = (Movie) parcelable[i];
                }
                mGridView.setAdapter(new MovieAdapter(this, movies));
            }
        }
    }

    /**
     * If a user sorts and sets
     * @return returns the sorted method from shared preference
     */
    private String getSortedMethod() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        return  sharedPreferences.getString(getString(R.string.pref_sort_method_key),
                getString(R.string.tmdb_sort_pop_desc));
    }

    /**
     * in this method i will get the objects from gridview and save to bundle
     * @param outState save the objects to bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int mGridViewCount = mGridView.getCount();
        if ( mGridViewCount > 0) {

            Movie[] movies = new Movie[ mGridViewCount];
            for (int i = 0; i <  mGridViewCount; i++) {
                movies[i] = (Movie) mGridView.getItemAtPosition(i);
            }

            outState.putParcelableArray(getString(R.string.movie_parcel), movies);
        }

        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id){
   // I decided to create this to bring the top rated movies. :) If you can suggest other better ways thank :)
            //popular movies
            case R.id.menu_sort_by_pop:
                setTitle(R.string.app_name);
                if(networkAvailable()) {

                    //Make sure you enter your key here
                    String apiKey = getString(R.string.my_api);

                    OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
                        @Override
                        public void onFetchMovie(Movie[] movies) {
                            mGridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                        }
                    };

                    MovieAsyncTask movieAsyncTask = new MovieAsyncTask(onTaskCompleted, apiKey);
                    movieAsyncTask.execute(getSortedMethod());
                }else {
                    Toast.makeText(this,getString(R.string.connect),Toast.LENGTH_SHORT).show();
                }
            return true;

                //top rated movies
            case R.id.menu_sort_by_avg:
                setTitle(R.string.top_rated);
                if(networkAvailable()) {

                    //Make sure you enter your key here
                    String apiKey = getString(R.string.my_api);

                    OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
                        @Override
                        public void onFetchMovie(Movie[] movies) {
                            mGridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                        }
                    };

                   TopRatedMovies movieAsyncTask = new TopRatedMovies(onTaskCompleted, apiKey);

                    movieAsyncTask.execute(getSortedMethod());
                }else {
                    Toast.makeText(this,getString(R.string.connect),Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);

    }


    /**
     * When a user changes the sort criteria (“most popular and highest rated”)
     * the main view gets updated correctly.
     * @param sortMethod the TMDB api method for sorting the movies
     */
    private void getMovies(String sortMethod){
        if(networkAvailable()) {

            //Make sure you enter your key here
            String apiKey = getString(R.string.my_api);

            OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
                @Override
                public void onFetchMovie(Movie[] movies) {
                    mGridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }
            };

            MovieAsyncTask movieAsyncTask = new MovieAsyncTask(onTaskCompleted, apiKey);
            movieAsyncTask.execute(sortMethod);
        }else {
            Toast.makeText(this,getString(R.string.connect),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method will check my internet connection
     * @return successful if connected to internet and false if not
     */
    private boolean networkAvailable() {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        }

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
