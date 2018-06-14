package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    //constructor
    public Genre() {

    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    private Genre(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    /**
     * get the movie id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get the genre name
     * @return name
     */
    public String getName() {
        return name;
    }
}