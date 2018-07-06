package com.madonasyombua.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step implements Parcelable {
    @JsonProperty("videoURL")
    private final String videoURL;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("id")
    private final int id;
    @JsonProperty("shortDescription")
    private final String shortDescription;
    @JsonProperty("thumbnailURL")
    private final String thumbnailURL;

    public Step() {
        this.videoURL = "";
        this.description = "";
        this.id = 0;
        this.shortDescription = "";
        this.thumbnailURL = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.thumbnailURL);
    }
    /**
     *  An unusual feature of Parcel is the ability to read and write active
     * objects.  For these objects the actual contents of the object is not
     * written, rather a special token referencing the object is written.  When
     * reading the object back from the Parcel, you do not get a new instance of
     * the object, but rather a handle that operates on the exact same object that
     * was originally written.  There are two forms of active objects available.</p>
     * @param in read
     */
    protected Step(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    /**
     * get video URL
     * @return video URL
     */
    public String getVideoURL() {
        return videoURL;
    }
    /**
     * get description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * get Id
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * get short description
     * @return short description
     */
    public String getShortDescription() {
        return shortDescription;
    }
    /**
     * get thumbnail URL
     * @return thumbnail URL
     */
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return "Step{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}