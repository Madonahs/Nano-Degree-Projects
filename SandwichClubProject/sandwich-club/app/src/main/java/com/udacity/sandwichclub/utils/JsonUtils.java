package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author madon
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();

    /**
     * @param json
     * @return Sandwich which takes in 6 paramters.
     * @throws JSONException
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //COMPLETED 6) Ensure JSON data is parsed correctly to a Sandwich object in JsonUtils
        //COMPLETED (7) JSON is parsed without using 3rd party libraries
        final String KEY_NAME = "name";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_MAINNAME = "mainName";
        final String KEY_INGRRDIENTS = "ingredients";
        final String KEY_ALSO_KNOW_AS= "alsoKnownAs";


        JSONObject sandwichObject = new JSONObject(json);
        //get the name JSONObject
        JSONObject name = sandwichObject.getJSONObject(KEY_NAME);

        //get origin
        String origin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);
        //get description
        String description = sandwichObject.getString(KEY_DESCRIPTION);
        //get image
        String image = sandwichObject.getString(KEY_IMAGE);
        //get nameString
        String nameString = name.getString(KEY_MAINNAME);

        //i will create method getArrayObject since our data structure is in a List of type string to get the ingredients and alsoknowas.
        List<String> ingredients = getArrayObject(sandwichObject.getJSONArray(KEY_INGRRDIENTS));

        List<String> asKnowAsName = getArrayObject(name.getJSONArray(KEY_ALSO_KNOW_AS));

        // return the Sandwich class which takes six parameters.
        return new Sandwich(nameString, asKnowAsName, origin, description, image, ingredients);
    }

    /**
     * I will create a method getArrayObject which takes in one parameter of type JSONArray
     * and loop through the jsonArray and add it to list
     * @param jsonArray
     * @return list
     *
     */
    private static List<String> getArrayObject(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                list.add(jsonArray.getString(i));
            }

        } catch (JSONException e ){

            Log.e(TAG,"Error parsing",e);

        }
        return list;
    }
}