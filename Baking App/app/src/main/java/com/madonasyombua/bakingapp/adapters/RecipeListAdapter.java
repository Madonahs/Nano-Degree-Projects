package com.madonasyombua.bakingapp.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madonasyombua.bakingapp.R;

import com.madonasyombua.bakingapp.models.Ingredients;
import com.madonasyombua.bakingapp.models.Recipe;
import com.madonasyombua.bakingapp.helpers.Listeners;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Recipe mRecipe;
    private final Listeners.OnItemClickListener mOnItemClickListener;

    //constructor takes Recipe and a Listener
    public RecipeListAdapter(Recipe recipe, Listeners.OnItemClickListener onItemClickListener) {
        this.mRecipe = recipe;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new RecipeListViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_ingredient_list_item, parent, false));
        } else {
            return new StepsAdapter.StepViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_step_list_item, parent, false));
        }

    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecipeListViewHolder) {
            RecipeListViewHolder viewHolder = (RecipeListViewHolder) holder;

            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                Ingredients ingredients = mRecipe.getIngredients().get(i);
                ingValue.append(String.format(Locale.getDefault(), "• %s (%d %s)", ingredients.getIngredient(), ingredients.getQuantity(), ingredients.getMeasure()));
                if (i != mRecipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }

            viewHolder.ingredientsTv.setText(ingValue.toString());
        } else if (holder instanceof StepsAdapter.StepViewHolder) {
            StepsAdapter.StepViewHolder viewHolder = (StepsAdapter.StepViewHolder) holder;
            viewHolder.mTvStepOrder.setText(String.valueOf(position - 1) + ".");
            viewHolder.mTvStepName.setText(mRecipe.getSteps().get(position - 1).getShortDescription());

            holder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position - 1);
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() + 1;
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_text)
        public TextView ingredientsTv;

        RecipeListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

    }

}
