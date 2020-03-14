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
package com.madonasyombua.myapplication.ui.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.ui.activities.MainActivity;
import com.madonasyombua.myapplication.ui.adapters.ReviewAdapter;
import com.madonasyombua.myapplication.ui.adapters.VideoAdapter;
import com.madonasyombua.myapplication.data.MovieContract;
import com.madonasyombua.myapplication.data.model.Genre;
import com.madonasyombua.myapplication.data.model.Movie;
import com.madonasyombua.myapplication.data.model.Video;
import com.madonasyombua.myapplication.utils.ImageUtils;
import com.madonasyombua.myapplication.utils.ItemDecoration;
import com.nex3z.flowlayout.FlowLayout;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
/**
 * @author madona syombua
 *
 */
public class MovieFragment extends Fragment {

    public static final String EXTRA_MOVIE_KEY = "extra_movie";

    private NestedScrollView mSvDetailsContainer;
    private Movie mMovie;
    @BindView(R.id.ivMovie) ImageView mIvMovie;
    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.tvReleaseDateValue) TextView mTvReleaseDateValue;
    @BindView(R.id.tvDurationValue) TextView mTvDurationValue;
    @BindView(R.id.tvVoteValue) TextView mTvVoteValue;
    @BindView(R.id.tvPlotValue) TextView mTvPlotValue;
    @BindView(R.id.ratingBar) MaterialRatingBar mRatingBar;
    @BindView(R.id.genresContainer) FlowLayout mGenresContainer;
    @BindView(R.id.rvVideos) RecyclerView mRvVideos;
    @BindView(R.id.tvReviewsTitle) TextView mTvReviewsTitle;
    @BindView(R.id.rvReviews) RecyclerView mRvReviews;
    FloatingActionButton mFbLike;
    private boolean mIsFavourite;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(EXTRA_MOVIE_KEY)) {
            mMovie = getArguments().getParcelable(EXTRA_MOVIE_KEY);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this,rootView);
        mSvDetailsContainer = rootView.findViewById(R.id.svDetailsContainer);
        mIsFavourite = isFavouriteMovie();

        if (mMovie != null) {
            initializeViews(rootView);
            populateUI();

        } else {
            closeOnError();
        }
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeViews(View rootView) {

        mFbLike = rootView.findViewById(R.id.fbLike);

        mFbLike.setOnClickListener (view -> switchFavouriteStatus());
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void populateUI() {
        CollapsingToolbarLayout appBarLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_layout);

        if (appBarLayout != null) {
            appBarLayout.setTitle(mMovie.getTitle());
        }

        switchFabStyle();

        final ImageView backDropImageView = getActivity().findViewById(R.id.backDropImage);
        if (backDropImageView != null) {
            backDropImageView.post(() -> Picasso.with(getActivity().getApplicationContext())
                    .load(ImageUtils. buildBackdropImage(mMovie.getBackdropPath(), backDropImageView.getWidth()))
                    .into(backDropImageView));
        }

        mTvTitle.setText(mMovie.getTitle());
        mTvReleaseDateValue.setText(mMovie.getReleaseDateLocalized(getActivity().getApplicationContext()));
        mTvDurationValue.setText(mMovie.getDuration());
        mTvVoteValue.setText(String.valueOf(mMovie.getVoteAverage()));
        mRatingBar.setRating((float) (mMovie.getVoteAverage() / 2));
        mTvPlotValue.setText(mMovie.getMovieOverview());

        for (Genre genre : mMovie.getGenres()) {
            TextView textView = new TextView(getActivity());
            textView.setText(genre.getName());
            mGenresContainer.addView(textView);
        }

        mIvMovie.post(() -> Picasso.with(getActivity().getApplicationContext())
                .load(ImageUtils.buildPosterImageUrl(mMovie.getMoviePosterPath(), mIvMovie.getWidth()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(mIvMovie));


        setUpVideosRecyclerView();
        setUpReviewsRecyclerView();
    }

    private void setUpVideosRecyclerView() {
        if (mMovie.getVideos().getResults().size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mRvVideos.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRvVideos.setLayoutManager(layoutManager);
            mRvVideos.setHasFixedSize(true);
            mRvVideos.addItemDecoration(new ItemDecoration((int) getResources().getDimension(R.dimen.video_list_items_margin), ItemDecoration.HORIZONTAL));

            mRvVideos.setAdapter(new VideoAdapter(mRvVideos.getContext(), mMovie.getVideos()));
            mRvVideos.setVisibility(View.VISIBLE);

            mRvVideos.setVisibility(View.VISIBLE);
            setHasOptionsMenu(true);
        }
    }

    private void setUpReviewsRecyclerView() {
        if (mMovie.getReviews().getResults().size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mRvVideos.getContext(), LinearLayoutManager.VERTICAL, false);
            mRvReviews.setLayoutManager(layoutManager);
            mRvReviews.setHasFixedSize(false);
            mRvReviews.addItemDecoration(new ItemDecoration((int) getResources().getDimension(R.dimen.movie_list_items_margin), ItemDecoration.VERTICAL));

            mRvReviews.setAdapter(new ReviewAdapter(mMovie.getReviews()));

            mTvReviewsTitle.setVisibility(View.VISIBLE);
            mRvReviews.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void closeOnError() {
        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.error_message, Toast.LENGTH_SHORT).show();

        // Not in TwoPane Mode so close the MovieListDetail
        if (!areOnTwoPaneMove()) {
            getActivity().finish();
        } else {
            mSvDetailsContainer.setVisibility(View.GONE);
        }
    }

    private void shareTrailer(Video video) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mMovie.getTitle() + " - " + video.getName());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + video.getKey());
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_trailer)));
    }

    private boolean areOnTwoPaneMove() {
        return (getActivity() instanceof MainActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isFavouriteMovie() {

        final Cursor cursor;
        cursor = Objects.requireNonNull(getContext()).getContentResolver()
                .query(MovieContract.MovieListEntry.CONTENT_URI, null, "movie_id=?", new String[]{String.valueOf(mMovie.getId())}, null);

        boolean result = Objects.requireNonNull(cursor).getCount() > 0;
        cursor.close();
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void switchFavouriteStatus() {

        if (mIsFavourite) {
            Uri uri = MovieContract.MovieListEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(String.valueOf(mMovie.getId())).build();
            int returnUri = Objects.requireNonNull(getContext()).getContentResolver().delete(uri, null, null);
            Logger.d("ReturnUri: " + returnUri);
            getContext().getContentResolver().notifyChange(uri, null);

            mIsFavourite = !mIsFavourite;
            switchFabStyle();
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), mMovie.getTitle() + " " + getString(R.string.removed_from_favourite), Toast.LENGTH_SHORT).show();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieListEntry.COLUMN_ID, mMovie.getId());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_TITLE, mMovie.getTitle());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_OVERVIEW, mMovie.getMovieOverview());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_POSTER_PATH, mMovie.getMoviePosterPath());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_BACKDROP_PATH, mMovie.getBackdropPath());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_RUNTIME, mMovie.getRuntime());
            contentValues.put(MovieContract.MovieListEntry.COLUMN_VOTE_AVERAGE, mMovie.getVoteAverage());

            Uri uri = Objects.requireNonNull(getContext()).getContentResolver().insert(MovieContract.MovieListEntry.CONTENT_URI, contentValues);
            if (uri != null) {
                mIsFavourite = !mIsFavourite;
                switchFabStyle();
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), mMovie.getTitle() + " " + getString(R.string.added_to_favourite), Toast.LENGTH_SHORT).show();
            } else {
                Logger.d("Uri null");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void switchFabStyle() {
        if (mIsFavourite) {
            mFbLike.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.heart));

        } else {
            mFbLike.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.heart_outline));

        }
    }

}
