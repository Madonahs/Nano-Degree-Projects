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
package com.madonasyombua.bakingapp.models

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Step : Parcelable {
    /**
     * get video URL
     * @return video URL
     */
    @JsonProperty("videoURL")
    @JvmField val videoURL: String?

    /**
     * get description
     * @return description
     */
    @JsonProperty("description")
    val description: String?

    /**
     * get Id
     * @return id
     */
    @JsonProperty("id")
    val id: Int

    /**
     * get short description
     * @return short description
     */
    @JsonProperty("shortDescription")
    val shortDescription: String?

    /**
     * get thumbnail URL
     * @return thumbnail URL
     */
    @JsonProperty("thumbnailURL")
    val thumbnailURL: String?

    constructor() {
        videoURL = ""
        description = ""
        id = 0
        shortDescription = ""
        thumbnailURL = ""
    }

    // Parcelable
    override fun describeContents(): Int {
        return 0
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or [.PARCELABLE_WRITE_RETURN_VALUE].
     */
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(videoURL)
        dest.writeString(description)
        dest.writeInt(id)
        dest.writeString(shortDescription)
        dest.writeString(thumbnailURL)
    }

    /**
     * An unusual feature of Parcel is the ability to read and write active
     * objects.  For these objects the actual contents of the object is not
     * written, rather a special token referencing the object is written.  When
     * reading the object back from the Parcel, you do not get a new instance of
     * the object, but rather a handle that operates on the exact same object that
     * was originally written.  There are two forms of active objects available.
     * @param in read
     */
    private constructor(`in`: Parcel) {
        videoURL = `in`.readString()
        description = `in`.readString()
        id = `in`.readInt()
        shortDescription = `in`.readString()
        thumbnailURL = `in`.readString()
    }

    override fun toString(): String {
        return "Step{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}'
    }

    companion object {
        val CREATOR: Parcelable.Creator<Step?> = object : Parcelable.Creator<Step?> {
            override fun createFromParcel(source: Parcel): Step? {
                return Step(source)
            }

            override fun newArray(size: Int): Array<Step?> {
                return arrayOfNulls(size)
            }
        }
    }
}