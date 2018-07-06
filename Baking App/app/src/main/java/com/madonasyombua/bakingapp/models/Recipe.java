package com.madonasyombua.bakingapp.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    @JsonProperty("image")
    private final String image;
    @JsonProperty("servings")
    private final int servings;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("ingredients")
    private final List<Ingredients> ingredients;
    @JsonProperty("id")
    private final int id;
    @JsonProperty("steps")
    private final List<Step> steps;

    public Recipe() {
        this.image = "";
        this.servings = 0;
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.id = 0;
        this.steps = new ArrayList<>();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    /**
     * get image method
     * @return the image provided by udacity inform of json data
     */
    public String getImage() {
        return image;
    }

    /**
     * get the servings
     * @return get the servings provided by udacity inform of json data
     */
    public int getServings() {
        return servings;
    }

    /**
     * get the name
     * @return name of the recipes
     */
    public String getName() {
        return name;
    }

    /**
     * get the list of Ingredients
     * @return ingredients
     */
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    /**
     * get the id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get the List of steps
     * @return list of steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * to base 64 String
     * @param recipe the recipe
     * @return Base.encode to string hence null
     */
    public static String toBase64String(Recipe recipe) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
        } catch (JsonProcessingException e) {
            Logger.e(e.getMessage());
        }
        return null;
    }

    public static Recipe fromBase64(String encoded) {
        if (!"".equals(encoded)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(encoded, 0), Recipe.class);
            } catch (IOException e) {
                Logger.e(e.getMessage());
            }
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("Recipe{image='%s', servings=%d, name='%s', ingredients=%s, id=%d, steps=%s}", image, servings, name, ingredients, id, steps);
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
        dest.writeString(this.image);
        dest.writeInt(this.servings);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeInt(this.id);
        dest.writeList(this.steps);
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
    protected Recipe(Parcel in) {
        this.image = in.readString();
        this.servings = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.id = in.readInt();
        this.steps = new ArrayList<>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

}