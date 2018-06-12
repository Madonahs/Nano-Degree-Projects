package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable {
    String id;
    String author;
    String content;
    String url;


    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public MovieReview(Parcel source) {
        this.id = source.readString();
        this.author = source.readString();
        this.content = source.readString();
        this.url = source.readString();
    }

    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>() {
        public MovieReview createFromParcel(Parcel source) {
            MovieReview movieReview = new MovieReview(source);
            return movieReview;
        }

        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

}
