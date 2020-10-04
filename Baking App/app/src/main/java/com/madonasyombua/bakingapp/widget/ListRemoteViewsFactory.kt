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
package com.madonasyombua.bakingapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.models.Recipe
import com.madonasyombua.bakingapp.utils.Prefs.loadRecipe

class ListRemoteViewsFactory(private val mContext: Context) : RemoteViewsFactory {
    private var recipe: Recipe? = null
    override fun onCreate() {}
    override fun onDataSetChanged() {
        recipe = loadRecipe(mContext)
    }

    override fun onDestroy() {}
    override fun getCount(): Int {
        return recipe!!.ingredients.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val row = RemoteViews(mContext.packageName, R.layout.baking_recipes_widget_list_item)
        row.setTextViewText(R.id.ingredient_item_text, recipe!!.ingredients[position]!!.ingredient)
        return row
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}