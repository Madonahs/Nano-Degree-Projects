package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResult implements Parcelable {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("poster_path")
    private String posterPath;
    //constructor
    public MovieResult() {

    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.posterPath);
    }

    private MovieResult(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.posterPath = in.readString();
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel source) {
            return new MovieResult(source);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };

    /**
     * get the id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * get the poster path
     * @return path
     */
    public String getPosterPath() {
        return posterPath;
    }
}