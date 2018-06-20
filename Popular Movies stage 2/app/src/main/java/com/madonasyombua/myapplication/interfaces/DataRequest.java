package com.madonasyombua.myapplication.interfaces;

import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataRequest {

    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<Movies> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movieId}?append_to_response=videos,reviews")
    Call<Movie> getMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}