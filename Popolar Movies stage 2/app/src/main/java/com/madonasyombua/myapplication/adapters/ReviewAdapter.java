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
        final TextView mTvReviewAuthor;
        final TextView mTvReviewContent;

        ReviewViewHolder(View itemView) {
            super(itemView);

            mTvReviewAuthor = itemView.findViewById(R.id.tvReviewAuthor);
            mTvReviewContent = itemView.findViewById(R.id.tvReviewContent);
        }
    }
}
