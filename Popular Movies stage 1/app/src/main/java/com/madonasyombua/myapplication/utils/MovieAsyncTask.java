package com.madonasyombua.myapplication.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.madonasyombua.myapplication.interfaces.OnTaskCompleted;
import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author madon
 */
public class MovieAsyncTask  extends AsyncTask<String,Void,Movie[]>{

    private final String TAG = MovieAsyncTask.class.getName();

    /**
     * The Api key should be private and never to be shared.
     * To fetch popular movies, you will use the API from themoviedb.org.
     * <p/>
     * If you don’t already have an account, you will need to create
     * one in order to request an API Key.
     */
    private String my_api_key;

    private OnTaskCompleted onTaskCompleted;

    private String movieJson = null;


    /**
     * this is my Constructor which takes in two args
     * @param taskCompleted this is the interface listener
     * @param api_key this is the TMDB Api Key
     */

    public MovieAsyncTask(OnTaskCompleted taskCompleted, String api_key){
        super();
        onTaskCompleted = taskCompleted;
        my_api_key = api_key;
    }

    @Override
    protected Movie[] doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null; //connectivity

        BufferedReader reader = null;

        //I need to start fetching data from the api
        try{

            URL url = getMovieApi(strings);
            //getting the connection
            httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();

            if(inputStream == null){ //if the input is null return null
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String addLine;
            while ((addLine = reader.readLine()) != null){

                stringBuilder.append(addLine).append("\n");//append a single line
            }

            //checking is it is = 0
            if(stringBuilder.length() == 0){
                return  null;
            }

            movieJson = stringBuilder.toString();

        }catch (IOException e){
            Log.e(TAG, String.valueOf(R.string.error), e);
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            //close the reader
            if(reader != null){
                try {

                    reader.close();

                }catch (IOException e){
                    Log.e(TAG, String.valueOf(R.string.error_while_closing),e);

                }

                try {
                    return getMovieFromJson(movieJson);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    /**
     * Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
     * original title
     * movie poster image thumbnail
     * <p/>
     * A plot synopsis (called overview in the api)
     * user rating (called vote_average in the api)
     * <p/>
     * release date
     * This method gets the movies from the Json data
     * @param movieJson the json string to be used
     * @return a list of movie objects
     * @throws JSONException
     */
    private Movie[] getMovieFromJson(String movieJson) throws JSONException{

        final String TAG_RESULTS = "results";
        final String TAG_ORIGINAL_TITLE = "original_title";
        final String TAG_POSTER_PATH = "poster_path";
        final String TAG_OVERVIEW = "overview";
        final String TAG_VOTE_AVERAGE = "vote_average";
        final String TAG_RELEASE_DATE = "release_date";

        // Get the array containing hte movies found
        JSONObject moviesJson = new JSONObject(movieJson);
        JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);

        // Create array of Movie objects that stores data from the JSON string
        Movie[] movies = new Movie[resultsArray.length()];

        // Traverse through movies one by one and get data
        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            movies[i] = new Movie();

            // Object contains all tags we're looking for
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            movies[i].setMovieOriginalTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setMoviePosterPath(movieInfo.getString(TAG_POSTER_PATH));
            movies[i].setMovieOverview(movieInfo.getString(TAG_OVERVIEW));
            movies[i].setMovieVote(movieInfo.getDouble(TAG_VOTE_AVERAGE));
            movies[i].setMovieRealease(movieInfo.getString(TAG_RELEASE_DATE));
        }

        return movies;
    }

    /**
     * I will create a method getMovieApi to help me access the api
     * @param strings to be used in the call
     * @return the Url(uri. to string)
     * @throws MalformedURLException
     */
    private URL getMovieApi(String[] strings) throws MalformedURLException {


        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
       //add baseURL
        String Sort_Parameter = "sort by";
        String Api_Key = "api_key";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(Sort_Parameter,strings[0])
                .appendQueryParameter(Api_Key,my_api_key)
                .build();


        return  new URL(uri.toString());

    }

    /**
     *
     * @param movies this will notify the UI
     */
    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        onTaskCompleted.onFetchMovie(movies);
    }
}


