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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.models.Recipe
import java.util.*

class RecipeListAdapter     //constructor takes Recipe and a Listener
(private val mRecipe: Recipe, private val mOnItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            RecipeListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_ingredient_list_item, parent, false))
        } else {
            StepsAdapter.StepViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_step_list_item, parent, false))
        }
    }

    @SuppressLint("RecyclerView", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecipeListViewHolder) {
            val ingValue = StringBuilder()
            for (i in mRecipe.ingredients.indices) {
                val ingredients = mRecipe.ingredients[i]
                ingValue.append(String.format(Locale.getDefault(), "• %s (%d %s)", ingredients?.ingredient, ingredients?.quantity, ingredients?.measure))
                if (i != mRecipe.ingredients.size - 1) ingValue.append("\n")
            }
            holder.ingredientsTv?.text = ingValue.toString()
        } else if (holder is StepsAdapter.StepViewHolder) {
            val viewHolder: StepsAdapter.StepViewHolder = holder
            viewHolder.mTvStepOrder.text = (position - 1).toString() + "."
            viewHolder.mTvStepName.text = (mRecipe.steps[position - 1]?.shortDescription ?: holder.itemView.setOnClickListener {
                v: View? -> mOnItemClickListener?.onItemClick(position - 1) }) as CharSequence?
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun getItemCount(): Int {
        return mRecipe.steps.size + 1
    }

    inner class RecipeListViewHolder internal constructor(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @BindView(R.id.ingredients_text)
        var ingredientsTv: TextView? = null

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }
}

private fun Any.onItemClick(i: Int) {

}
