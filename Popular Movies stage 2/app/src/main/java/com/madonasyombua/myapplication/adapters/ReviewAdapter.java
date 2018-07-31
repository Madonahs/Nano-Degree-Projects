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
package com.madonasyombua.myapplication.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.model.MovieReview;
import com.madonasyombua.myapplication.model.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author madona syombua
 *
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final MovieReview mReviews;

    public ReviewAdapter(MovieReview reviews) {
        this.mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        final Review review = mReviews.getResults().get(position);

        holder.mTvReviewAuthor.setText(review.getAuthor());
        holder.mTvReviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.getResults().size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvReviewAuthor)
        TextView mTvReviewAuthor;
        @BindView(R.id.tvReviewContent)
        TextView mTvReviewContent;

        ReviewViewHolder(View itemView) {
            super(itemView);   
        ButterKnife.bind(this, itemView);
        }
    }
}
