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
import com.fasterxml.jackson.annotation.JsonProperty

class Ingredients : Parcelable {
    /**
     * get Quantity method
     * @return quantity
     */
    @JsonProperty("quantity")
    @JvmField val quantity: Int

    /**
     * get Measure method
     * @return measure
     */
    @JsonProperty("measure")
    val measure: String?

    /**
     * get ingredients method
     * @return ingredient
     */
    @JsonProperty("ingredient")
    val ingredient: String?

    constructor() {
        quantity = 0
        measure = ""
        ingredient = ""
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of [.writeToParcel],
     * the return value of this method must include the
     * [.CONTENTS_FILE_DESCRIPTOR] bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return String.format("Ingredients{quantity=%d, measure='%s', ingredient='%s'}", quantity, measure, ingredient)
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or [.PARCELABLE_WRITE_RETURN_VALUE].
     */
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(quantity)
        dest.writeString(measure)
        dest.writeString(ingredient)
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
        quantity = `in`.readInt()
        measure = `in`.readString()
        ingredient = `in`.readString()
    }

    companion object {
        val CREATOR: Parcelable.Creator<Ingredients?> = object : Parcelable.Creator<Ingredients?> {
            override fun createFromParcel(source: Parcel): Ingredients? {
                return Ingredients(source)
            }

            override fun newArray(size: Int): Array<Ingredients?> {
                return arrayOfNulls(size)
            }
        }
    }
}