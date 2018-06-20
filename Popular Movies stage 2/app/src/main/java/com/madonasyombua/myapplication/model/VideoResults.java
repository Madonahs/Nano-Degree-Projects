package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoResults implements Parcelable {
    @JsonProperty("results")
    private final List<Video> results;

    public VideoResults() {
        this.results = new ArrayList<>();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
    }

    VideoResults(Parcel in) {
        this.results = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<VideoResults> CREATOR = new Creator<VideoResults>() {
        @Override
        public VideoResults createFromParcel(Parcel source) {
            return new VideoResults(source);
        }

        @Override
        public VideoResults[] newArray(int size) {
            return new VideoResults[size];
        }
    };

    /**
     * get the results
     * @return results
     */

    public List<Video> getResults() {
        return results;
    }
}