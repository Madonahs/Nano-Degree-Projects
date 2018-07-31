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
package com.madonasyombua.bakingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import com.madonasyombua.bakingapp.R;

import com.madonasyombua.bakingapp.models.Recipe;
import com.madonasyombua.bakingapp.utils.GlideApp;
import com.madonasyombua.bakingapp.helpers.Listeners;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private final Context mContext;
    private final List<Recipe> mRecipes;
    private final Listeners.OnItemClickListener mOnItemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipes, Listeners.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mRecipes = recipes;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        holder.recipeName.setText(mRecipes.get(position).getName());
        holder.servings.setText(mContext.getString(R.string.servings, mRecipes.get(position).getServings()));

        String recipeImage = mRecipes.get(position).getImage();
        if (!recipeImage.isEmpty()) {
            GlideApp.with(mContext)
                    .load(recipeImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.cake_image)
                    .into(holder.mIvRecipe);
        }

        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name_text)
        TextView recipeName;

        @BindView(R.id.servings_text)
        public TextView servings;

        @BindView(R.id.recipe_image)
        AppCompatImageView mIvRecipe;

        RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
