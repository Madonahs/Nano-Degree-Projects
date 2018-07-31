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
 */
package com.madonasyombua.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author madona syombua
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review implements Parcelable {
    @JsonProperty("author")
    private final String author;
    @JsonProperty("id")
    private final String id;
    @JsonProperty("content")
    private final String content;
    @JsonProperty("url")
    private final String url;

    public Review() {
        this.author = "";
        this.id = "";
        this.content = "";
        this.url = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    private Review(Parcel in) {
        this.author = in.readString();
        this.id = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    /**
     * get the author os the review
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * get the id
     * @return id
     */

    public String getId() {
        return id;
    }

    /**
     * get the content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * get the url
     * @return url
     */
    public String getUrl() {
        return url;
    }
}