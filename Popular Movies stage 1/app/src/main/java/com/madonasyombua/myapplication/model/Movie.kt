package com.madonasyombua.myapplication.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author madon
 */
class Movie : Parcelable {
    /**
     * this method gets the Title
     *
     *
     * @return movie Title
     */
    //movie objects
    var title: String? = null
        private set
    private var moviePosterPath: String? = null

    /**
     * This method return the details on the movie
     *
     *
     * @return Movie description
     */
    var movieOverview: String? = null
        private set
    /**
     * This method gets movie votes
     *
     *
     * @return all the votes the movie got
     */
    /**
     * we will get this from TMDB
     * @param movieVote1 this gives the average movie vote
     */
    var movieVote: Double? = null

    /**
     * This method gets the date from TMDB
     *
     *
     * @return get the release date
     */
    var movieRelease: String? = null
        private set

    //Constructor
    constructor() {}

    /**
     * This method returns the URL string where the poster will be loaded
     * The base URL will look like: http://image.tmdb.org/t/p/.
     * Then you will need a ‘size’, which will be one of the following: "w92", "w154", "w185", "w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
     *
     *
     * And finally the poster path returned by the query, in this case “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”
     *
     *
     * @return URL string
     */
    fun getMoviePosterPath(): String {
        val MOVIE_TMDB_URL = "https://image.tmdb.org/t/p/w185"
        return MOVIE_TMDB_URL + moviePosterPath
    }

    /**
     * Sets original movie name from TMDB
     * @param originalTitle this is the movie original title
     */
    fun setMovieOriginalTitle(originalTitle: String?) {
        title = originalTitle
    }

    /**
     * This method gets the Score from the TMDB
     *
     *
     * @return the Vote average
     */
    val voteAverage: String
        get() = movieVote.toString() + "/10"

    /**
     * Takes path from TMDB and sets the path
     * @param posterPath this is the poster path
     */
    fun setMoviePosterPath(posterPath: String?) {
        moviePosterPath = posterPath
    }

    /**
     * Take the Movie title and sets it.
     * @param overview this is the movie description from TMDB
     */
    fun setMovieOverview(overview: String) {
        //check if it equals null
        if (overview != "null") {
            movieOverview = overview
        }
    }

    /**
     * we will set the original date based on the data we collect from TMDB
     * @param movie_Release_Date this is the original release date from TMDB
     */
    fun setMovieRealease(movie_Release_Date: String) {
        //we will check to make sure the data is not null
        if (movie_Release_Date != "null") {
            movieRelease = movie_Release_Date
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * This methods is from Parcel since we implemented our model with it
     *
     *
     * @param dest the parcel
     * @param flags and flags
     */
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(moviePosterPath)
        dest.writeString(movieOverview)
        dest.writeString(movieRelease)
        dest.writeValue(movieVote)
    }

    /**
     *
     * @param in parcel read string
     */
    private constructor(`in`: Parcel) {
        title = `in`.readString()
        moviePosterPath = `in`.readString()
        movieOverview = `in`.readString()
        movieRelease = `in`.readString()
        movieVote = `in`.readValue(Double::class.javaPrimitiveType!!.classLoader) as Double
    }

    companion object {

        val date = "yyyy-MM-dd"
            get() = Companion.field
        val CREATOR: Parcelable.Creator<Movie?> = object : Parcelable.Creator<Movie?> {
            override fun createFromParcel(`in`: Parcel): Movie? {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }
    }
}