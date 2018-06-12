package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author madon
 */
public class Movie implements Parcelable{
    //movie objects
    private String movieOriginalTitle;
    private String moviePosterPath;
    private String movieOverview;
    private Double movieVote;
    private String movieRelease;
    private int id;
    private String backdropPath;

    private static final String Date_TMDB = "yyyy-MM-dd";

    //Constructor
    public Movie() {
    }

    /**
     * this method gets the Title
     *  <p/>
     * @return movie Title
     */
    public String getTitle(){

        return movieOriginalTitle;

    }

    public String getBackdropPath() {

        String BASE_URL_IMAGE_BACKDROP = "http://image.tmdb.org/t/p/w780";

        return BASE_URL_IMAGE_BACKDROP + backdropPath;
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
    public String getMoviePosterPath(){

        final String MOVIE_TMDB_URL = "https://image.tmdb.org/t/p/w185";

        return  MOVIE_TMDB_URL + moviePosterPath;
    }

    /**
     * This method return the details on the movie
     *  <p/>
     * @return Movie description
     */

    public String getMovieOverview(){

        return movieOverview;
    }

    /**
     * This method gets movie votes
     * <p/>
     * @return all the votes the movie got
     */
    public Double getMovieVote(){

        return movieVote;
    }

    /**
     * This method gets the date from TMDB
     *  <p/>
     * @return get the release date
     */
    public String getMovieRelease(){

        return movieRelease;
    }
    /**
     * Sets original movie name from TMDB
     * @param originalTitle this is the movie original title
     */
    public void setMovieOriginalTitle(String originalTitle){

        movieOriginalTitle = originalTitle;

    }

    /**
     * This method gets the Score from the TMDB
     * <p/>
     * @return the Vote average
     */
    public String getVoteAverage(){

        return String.valueOf(getMovieVote()) + "/10";

    }
    /**
     * Takes path from TMDB and sets the path
     * @param posterPath this is the poster path
     */
    public  void setMoviePosterPath (String posterPath){
        moviePosterPath = posterPath;
    }

    /**
     *
     * @return the date format
     */
    public String getDate(){

        return Date_TMDB;
    }
    /**
     * Take the Movie title and sets it.
     * @param overview this is the movie description from TMDB
     */
    public void setMovieOverview(String overview){
     //check if it equals null
        if(!overview.equals("null")){
            movieOverview =overview;
        }

    }

    /**
     * we will get this from TMDB
     * @param movieVote1 this gives the average movie vote
     */
    public void setMovieVote(Double movieVote1){
        movieVote = movieVote1;
    }

    /**
     * we will set the original date based on the data we collect from TMDB
     * @param movie_Release_Date this is the original release date from TMDB
     */
    public void setMovieRealease(String movie_Release_Date){
        //we will check to make sure the data is not null


        if(!movie_Release_Date.equals("null")){
            movieRelease = movie_Release_Date;
        }

    }

    public int getId() {
        return id;
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
        dest.writeString(movieOriginalTitle);
        dest.writeString(moviePosterPath);
        dest.writeString(movieOverview);
        dest.writeString(movieRelease);
        dest.writeValue(movieVote);
        dest.writeValue(backdropPath);
        dest.writeValue(id);
    }

    /**
     *
     * @param in parcel read string
     */
    private Movie(Parcel in){
        movieOriginalTitle = in.readString();
        moviePosterPath = in.readString();
        movieOverview = in.readString();
        movieRelease = in.readString();
        movieVote = (double) in.readValue(double.class.getClassLoader());
        backdropPath= in.readString();
        id = in.readInt();


    }
}
