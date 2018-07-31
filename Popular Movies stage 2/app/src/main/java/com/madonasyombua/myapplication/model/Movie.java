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
 */package com.madonasyombua.myapplication.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * @author madona syombua
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Parcelable{
    //movie objects
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("videos")
    private VideoResults videos;
    @JsonProperty("reviews")
    private MovieReview reviews;
    @JsonProperty("genres")
    private List<Genre> genres;

    //Constructor
    public Movie() {

    }

    /**
     * the movie constructor which takes in all the above needed arguments
     * @param id the movie id
     * @param title the movie title
     * @param overview the movie overview
     * @param posterPath the movie poster path
     * @param backdropPath the movie backdrop image
     * @param releaseDate the movie release date
     * @param runtime the runtime of the movie
     * @param voteAverage the average vote the movie got
     * @param videos the movie trailers
     * @param reviews the movie reviews
     * @param genres the genre, is it adult, child?...
     */
    public Movie(int id, String title, String overview, String posterPath, String backdropPath,
                 String releaseDate, int runtime, double voteAverage,
                 VideoResults videos, MovieReview reviews, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.voteAverage = voteAverage;
        this.videos = videos;
        this.reviews = reviews;
        this.genres = genres;
    }
    public int getId() {
        return id;
    }
    /**
     * this method gets the Title
     *  <p/>
     * @return movie Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method return the details on the movie
     *  <p/>
     * @return Movie description
     */
    public String getMovieOverview() {
        return overview;
    }
    /**
     * This method returns the URL string where the poster will be loaded
     * The base URL will look like: http://image.tmdb.org/t/p/.
     * Then you will need a ‘size’, which will be one of the following: "w92", "w154", "w185", "w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
     * <p/>
     * And finally the poster path returned by the query, in this case “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”
     *  <p/>
     * @return URL string
     */
    public String getMoviePosterPath() {
        return posterPath;
    }

    /**
     * get the back drop image
     * @return back drop image
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * get the movie release date
     * @return release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * getting the localized date due to time zones
     * @param context context
     * @return formatted date
     */
    public String getReleaseDateLocalized(Context context) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        Date date;
        try {
            date = sdf.parse(releaseDate);
        } catch (ParseException e) {
            return releaseDate;
        }
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        return dateFormat.format(date);
    }

    /**
     * get the runtime of the movie
     * @return if it is 1hr +
     */
    public int getRuntime() {
        return runtime;
    }
    /**
     * This method gets movie votes
     * <p/>
     * @return all the votes the movie got
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * get the video trailers
     * @return videos
     */
    public VideoResults getVideos() {
        return videos;
    }

    /**
     * get the movie reviews
     * @return movie reviews
     */
    public MovieReview getReviews() {
        return reviews;
    }

    /**
     * get the genre which
     * @return genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * get the duration of the movie
     * @return runtime + minutes
     */
    public String getDuration() {

        return String.valueOf(runtime) + " min";
    }



    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This methods is from Parcel since we implemented our model with it
     * <p/>
     * @param dest the parcel
     * @param flags and flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.runtime);
        dest.writeDouble(this.voteAverage);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.reviews, flags);
        dest.writeTypedList(this.genres);
    }

    /**
     *
     * @param in parcel read string
     */
    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.runtime = in.readInt();
        this.voteAverage = in.readDouble();
        this.videos = in.readParcelable(VideoResults.class.getClassLoader());
        this.reviews = in.readParcelable(MovieReview.class.getClassLoader());
        this.genres = in.createTypedArrayList(Genre.CREATOR);
    }
}
