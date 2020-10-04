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
package com.madonasyombua.bakingapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.adapters.RecipesAdapter.RecipeViewHolder
import com.madonasyombua.bakingapp.helpers.Listeners
import com.madonasyombua.bakingapp.models.Recipe


class RecipesAdapter(private val mContext: Context, private val mRecipes: List<Recipe>, private val mOnItemClickListener: Listeners.OnItemClickListener?) : RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_list_item, parent, false)
        return RecipeViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeName?.text = mRecipes[position].name
        holder.servings?.text = mContext.getString(R.string.servings, mRecipes[position].servings)
        val recipeImage = mRecipes[position].image
        if (!recipeImage?.isEmpty()!!) {
            holder.mIvRecipe?.let {
                Glide.with(mContext)
                        .load(recipeImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.cake_image)
                        .into(it)
            }
        }
        holder.itemView.setOnClickListener { v: View? -> mOnItemClickListener?.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return mRecipes.size
    }

    inner class RecipeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @BindView(R.id.recipe_name_text)
        var recipeName: TextView? = null

        @BindView(R.id.servings_text)
        var servings: TextView? = null

        @BindView(R.id.recipe_image)
        var mIvRecipe: AppCompatImageView? = null

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }
}