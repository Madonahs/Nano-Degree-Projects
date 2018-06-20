package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Video implements Parcelable {
    @JsonProperty("site")
    private String site;
    @JsonProperty("size")
    private int size;
    @JsonProperty("iso_3166_1")
    private String iso31661;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("iso_639_1")
    private String iso6391;
    @JsonProperty("key")
    private String key;

    public Video() {
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.iso31661);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.iso6391);
        dest.writeString(this.key);
    }

    private Video(Parcel in) {
        this.site = in.readString();
        this.size = in.readInt();
        this.iso31661 = in.readString();
        this.name = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.iso6391 = in.readString();
        this.key = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };


    /**
     * get the size of the video
     * @return size
     */
    public int getSize() {
        return size;
    }


    /**
     * get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * get the type
     * @return type
     */
    public String getType() {
        return type;
    }

    public String getIso6391() {
        return iso6391;
    }

    /**
     * get the key
     * @return key
     */
    public String getKey() {
        return key;
    }

}