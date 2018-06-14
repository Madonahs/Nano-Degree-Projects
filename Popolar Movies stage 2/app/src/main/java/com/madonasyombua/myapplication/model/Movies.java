package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies implements Parcelable {
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("results")
    private final List<MovieResult> results;

    //constructor
    public Movies() {
        this.page = 0;
        this.results = new ArrayList<>();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeTypedList(this.results);
    }

    private Movies(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(MovieResult.CREATOR);
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    /**
     * get the page
     * @return page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * get the results
     * @return results
     */
    public List<MovieResult> getResults() {
        return results;
    }

    /**
     *
     * @param movies movies
     */
    public void appendMovies(Movies movies) {
        this.page = movies.getPage();
        this.results.addAll(movies.getResults());
    }


}