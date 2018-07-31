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
package com.madonasyombua.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.madonasyombua.bakingapp.models.Recipe;
import com.madonasyombua.bakingapp.R;


public class Prefs {

    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(context.getString(R.string.widget_recipe_key), Recipe.toBase64String(recipe));

        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String recipeBase64 = prefs.getString(context.getString(R.string.widget_recipe_key), "");

        return "".equals(recipeBase64) ? null : Recipe.fromBase64(prefs.getString(context.getString(R.string.widget_recipe_key), ""));
    }
}
