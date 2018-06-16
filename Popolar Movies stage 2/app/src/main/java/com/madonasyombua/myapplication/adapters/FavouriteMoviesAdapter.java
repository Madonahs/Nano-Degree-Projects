package com.madonasyombua.myapplication.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.activities.MainActivity;
import com.madonasyombua.myapplication.activities.MovieListDetail;
import com.madonasyombua.myapplication.fragments.MovieFragment;
import com.madonasyombua.myapplication.helpers.MovieContract;
import com.madonasyombua.myapplication.interfaces.DBUpdateListener;
import com.madonasyombua.myapplication.model.Genre;
import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.model.MovieReview;
import com.madonasyombua.myapplication.model.VideoResults;
import com.madonasyombua.myapplication.network.MoviesApiManager;
import com.madonasyombua.myapplication.utils.ImageUtils;
import com.madonasyombua.myapplication.utils.Network;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FavouriteMoviesAdapter  extends RecyclerView.Adapter<FavouriteMoviesAdapter.MovieViewHolder> {
    private final MainActivity mParentActivity;
    private final boolean mTwoPane;

    private final List<Movie> mFavouriteMovies;
    private Call<Movie> callRequest;


    public FavouriteMoviesAdapter(MainActivity parent, Cursor cursor, boolean twoPane) {
        this.mParentActivity = parent;
        this.mTwoPane = twoPane;

        mFavouriteMovies = new ArrayList<>();
        loadMoviesFromCursor(cursor);

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        final int pos = position;
        holder.mIvMovie.post(() -> Picasso.with(mParentActivity.getApplicationContext())
                .load(ImageUtils.buildPosterImageUrl(mFavouriteMovies.get(pos).getMoviePosterPath(), holder.mIvMovie.getWidth()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.mIvMovie));

        holder.itemView.setOnClickListener(view -> {
            if (callRequest != null) {
                callRequest.cancel();
            }
            getMovieAndShowDetails(pos, holder);
        });

        if (position == 0 && mTwoPane) {
            holder.itemView.callOnClick();
        }
    }

    @Override
    public int getItemCount() {
        return mFavouriteMovies.size();
    }


    private void getMovieAndShowDetails(final int position, final MovieViewHolder movieViewHolder) {
        if (Network.isNetworkAvailable(mParentActivity)) {
            movieViewHolder.showProgress(true);

            callRequest = MoviesApiManager.getInstance().getMovie(mFavouriteMovies.get(position).getId(), new DBUpdateListener<Movie>() {
                @Override
                public void onResponse(Movie result) {
                    if (result != null) {
                        showMovieDetails(result);
                    }
                    callRequest = null;
                    movieViewHolder.showProgress(false);
                }

                @Override
                public void onCancel() {
                    callRequest = null;
                    movieViewHolder.showProgress(false);
                }
            });
        } else {
            showMovieDetails(mFavouriteMovies.get(position));
        }


    }

    private void showMovieDetails(Movie movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieFragment.EXTRA_MOVIE_KEY, movie);
            MovieFragment fragment = new MovieFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movieDetailContainer, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mParentActivity, MovieListDetail.class);
            intent.putExtra(MovieFragment.EXTRA_MOVIE_KEY, movie);

            mParentActivity.startActivity(intent);
        }
    }

    /**
     * load movies from cursor method
     * @param cursor cursor
     */
    private void loadMoviesFromCursor(Cursor cursor) {
        for (int i = 0; i < cursor.getCount(); i++) {
            int movieIdIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_TITLE);
            int overviewIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_OVERVIEW);
            int posterPathIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_POSTER_PATH);
            int backdropPathIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_BACKDROP_PATH);
            int releaseDateIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_RELEASE_DATE);
            int runtimeIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_RUNTIME);
            int voteAverageIndex = cursor.getColumnIndex(MovieContract.MovieListEntry.COLUMN_VOTE_AVERAGE);

            cursor.moveToPosition(i);

            mFavouriteMovies.add(new Movie(
                    cursor.getInt(movieIdIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(overviewIndex),
                    cursor.getString(posterPathIndex),
                    cursor.getString(backdropPathIndex),
                    cursor.getString(releaseDateIndex),
                    cursor.getInt(runtimeIndex),
                    cursor.getInt(voteAverageIndex),
                    new VideoResults(),
                    new MovieReview(),
                    new ArrayList<>()
            ));
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIvMovie;
        private final LinearLayout mProgressBarContainer;

        MovieViewHolder(View itemView) {
            super(itemView);

            mProgressBarContainer = itemView.findViewById(R.id.progressBarContainer);
            mIvMovie = itemView.findViewById(R.id.cvVideo);
        }

        public void showProgress(Boolean show) {
            mProgressBarContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

}

