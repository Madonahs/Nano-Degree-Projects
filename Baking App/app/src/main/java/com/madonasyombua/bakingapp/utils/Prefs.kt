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
package com.madonasyombua.bakingapp.utils

import android.content.Context
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.models.Recipe
import com.madonasyombua.bakingapp.models.Recipe.Companion.fromBase64
import com.madonasyombua.bakingapp.models.Recipe.Companion.toBase64String

object Prefs {
    private const val PREFS_NAME = "prefs"
    @JvmStatic
    fun saveRecipe(context: Context, recipe: Recipe?) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        prefs.putString(context.getString(R.string.widget_recipe_key), toBase64String(recipe))
        prefs.apply()
    }

    @JvmStatic
    fun loadRecipe(context: Context): Recipe? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val recipeBase64 = prefs.getString(context.getString(R.string.widget_recipe_key), "")
        return if ("" == recipeBase64) null else fromBase64(prefs.getString(context.getString(R.string.widget_recipe_key), "")!!)
    }
}