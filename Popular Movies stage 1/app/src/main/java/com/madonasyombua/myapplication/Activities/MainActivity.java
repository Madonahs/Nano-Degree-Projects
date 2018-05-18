package com.madonasyombua.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.madonasyombua.myapplication.Adapters.MovieAdapter;
import com.madonasyombua.myapplication.Interfaces.OnTaskCompleted;
import com.madonasyombua.myapplication.Model.Movie;
import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.Utils.MovieAsyncTask;

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
        }
    }

    /**
     * If a user sorts and sets
     * @return returns the sorted method from shared preference
     */
    private String getSortedMethod() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        return sharedPreferences.getString(getString(R.string.sort_method_key),getString(R.string.sort_pop_dec));
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

    /**
     *
     * @param sortMethod sorted method to be saved
     */
    private void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_sort_method_key), sortMethod);
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id){

            case R.id.sort_by_pop:
                updateSharedPrefs(getString(R.string.sort_pop_dec));
                getMovies(getSortedMethod());
                return true;


            case R.id.sort_by_avg:
                updateSharedPrefs(getString(R.string.sort_avg_dec));
                getMovies(getSortedMethod());
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
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
