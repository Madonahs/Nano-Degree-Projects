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

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.madonasyombua.bakingapp.models.Ingredients
import com.orhanobut.logger.Logger
import java.io.IOException
import java.util.*

class Recipe : Parcelable {
    /**
     * get image method
     * @return the image provided by udacity inform of json data
     */
    @JsonProperty("image")
    @JvmField val image: String?

    /**
     * get the servings
     * @return get the servings provided by udacity inform of json data
     */
    @JsonProperty("servings")
    val servings: Int

    /**
     * get the name
     * @return name of the recipes
     */
    @JsonProperty("name")
    val name: String?

    /**
     * get the list of Ingredients
     * @return ingredients
     */
    @JsonProperty("ingredients")
    val ingredients: List<Ingredients?>

    /**
     * get the id
     * @return id
     */
    @JsonProperty("id")
    val id: Int

    /**
     * get the List of steps
     * @return list of steps
     */
    @JsonProperty("steps")
    val steps: List<Step?>

    constructor() {
        image = ""
        servings = 0
        name = ""
        ingredients = ArrayList()
        id = 0
        steps = ArrayList()
    }

    // Parcelable
    override fun describeContents(): Int {
        return 0
    }

    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return String.format("Recipe{image='%s', servings=%d, name='%s', ingredients=%s, id=%d, steps=%s}", image, servings, name, ingredients, id, steps)
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or [.PARCELABLE_WRITE_RETURN_VALUE].
     */
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(image)
        dest.writeInt(servings)
        dest.writeString(name)
        dest.writeList(ingredients)
        dest.writeInt(id)
        dest.writeList(steps)
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
        image = `in`.readString()
        servings = `in`.readInt()
        name = `in`.readString()
        ingredients = ArrayList()
        `in`.readList(ingredients, Ingredients::class.java.classLoader)
        id = `in`.readInt()
        steps = ArrayList()
        `in`.readList(steps, Step::class.java.classLoader)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Recipe?> = object : Parcelable.Creator<Recipe?> {
            override fun createFromParcel(source: Parcel): Recipe? {
                return Recipe(source)
            }

            override fun newArray(size: Int): Array<Recipe?> {
                return arrayOfNulls(size)
            }
        }

        /**
         * to base 64 String
         * @param recipe the recipe
         * @return Base.encode to string hence null
         */
        @JvmStatic
        fun toBase64String(recipe: Recipe?): String? {
            val mapper = ObjectMapper()
            try {
                return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0)
            } catch (e: JsonProcessingException) {
                Logger.e(e.message)
            }
            return null
        }

        @JvmStatic
        fun fromBase64(encoded: String): Recipe? {
            if ("" != encoded) {
                val mapper = ObjectMapper()
                try {
                    return mapper.readValue(Base64.decode(encoded, 0), Recipe::class.java)
                } catch (e: IOException) {
                    Logger.e(e.message)
                }
            }
            return null
        }
    }
}