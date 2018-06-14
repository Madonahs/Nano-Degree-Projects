package com.madonasyombua.myapplication.network;

import android.support.annotation.NonNull;

import com.madonasyombua.myapplication.interfaces.DBUpdateListener;
import com.madonasyombua.myapplication.interfaces.DataRequest;
import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.model.Movies;
import com.madonasyombua.myapplication.utils.Constants;
import com.orhanobut.logger.Logger;

import java.io.Serializable;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class MoviesApiManager implements Serializable {
    private static volatile MoviesApiManager sharedInstance = new MoviesApiManager();

    private DataRequest movieApiService;

    private MoviesApiManager() {
        //Prevent from the reflection api.
        if (sharedInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.TMDB_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        movieApiService = retrofit.create(DataRequest.class);
    }

    public static MoviesApiManager getInstance() {
        if (sharedInstance == null) {
            synchronized (MoviesApiManager.class) {
                if (sharedInstance == null) sharedInstance = new MoviesApiManager();
            }
        }

        return sharedInstance;
    }

    public enum SortBy {
        MostPopular(0),
        TopRated(1),
        Favourite(2);

        private final int mValue;

        SortBy(int value) {
            this.mValue = value;
        } // Constructor

        public int id() {
            return mValue;
        }                  // Return enum index

        public static SortBy fromId(int value) {
            for (SortBy color : values()) {
                if (color.mValue == value) {
                    return color;
                }
            }
            return MostPopular;
        }
    }

    public Call<Movie> getMovie(int movieId, final DBUpdateListener<Movie> moviesApiCallback) {
        Call<Movie> call = movieApiService.getMovie(movieId, Constants.TMDB_API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Logger.e("Request was cancelled");
                    moviesApiCallback.onCancel();
                } else {
                    Logger.e(t.getMessage());
                    moviesApiCallback.onResponse(null);
                }
            }
        });

        return call;
    }

    public void getMovies(SortBy sortBy, int page, DBUpdateListener<Movies> moviesApiCallback) {

        switch (sortBy) {
            case MostPopular:
                getPopularMovies(page, moviesApiCallback);
                break;
            case TopRated:
                getTopRatedMovies(page, moviesApiCallback);
                break;
        }

    }

    private void getPopularMovies(int page, final DBUpdateListener<Movies> moviesApiCallback) {
        movieApiService.getPopularMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Logger.e("Request was cancelled");
                    moviesApiCallback.onCancel();
                } else {
                    Logger.e(t.getMessage());
                    moviesApiCallback.onResponse(null);
                }
            }

        });
    }

    private void getTopRatedMovies(int page, final DBUpdateListener<Movies> moviesApiCallback) {
        movieApiService.getTopRatedMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Logger.e("Request was cancelled");
                    moviesApiCallback.onCancel();
                } else {
                    Logger.e(t.getMessage());
                    moviesApiCallback.onResponse(null);
                }
            }

        });
    }

}