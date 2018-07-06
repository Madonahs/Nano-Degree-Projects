package com.madonasyombua.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Ingredients implements Parcelable {
    @JsonProperty("quantity")
    private final int quantity;
    @JsonProperty("measure")
    private final String measure;
    @JsonProperty("ingredient")
    private final String ingredient;

    public Ingredients() {
        this.quantity = 0;
        this.measure = "";
        this.ingredient = "";
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    /**
     * get Quantity method
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * get Measure method
     * @return measure
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * get ingredients method
     * @return ingredient
     */

    public String getIngredient() {
        return ingredient;
    }


    @Override
    public String toString() {
        return String.format("Ingredients{quantity=%d, measure='%s', ingredient='%s'}", quantity, measure, ingredient);
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
        dest.writeInt(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
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
    private Ingredients(Parcel in) {
        this.quantity = in.readInt();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

}