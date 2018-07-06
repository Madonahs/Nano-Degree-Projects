package com.madonasyombua.bakingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import com.madonasyombua.bakingapp.R;
import com.madonasyombua.bakingapp.fragments.RecipeStepFragment;
import com.madonasyombua.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final List<Step> mSteps;

    public StepsAdapter(Context context, List<Step> steps, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepFragment.STEP_KEY, mSteps.get(position));
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(mContext.getString(R.string.step), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_order_text)
        public TextView mTvStepOrder;

        @BindView(R.id.step_name_text)
        public TextView mTvStepName;

        StepViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}