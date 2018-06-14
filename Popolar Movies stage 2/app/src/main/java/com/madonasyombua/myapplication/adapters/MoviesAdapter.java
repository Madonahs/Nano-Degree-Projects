package com.madonasyombua.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madonasyombua.myapplication.activities.MainActivity;
import com.madonasyombua.myapplication.activities.MovieListDetail;
import com.madonasyombua.myapplication.fragments.MovieFragment;
import com.madonasyombua.myapplication.interfaces.DBUpdateListener;
import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.model.Movies;
import com.madonasyombua.myapplication.network.MoviesApiManager;
import com.madonasyombua.myapplication.utils.ImageUtils;
import com.madonasyombua.myapplication.utils.Network;
import com.squareup.picasso.Picasso;

import retrofit2.Call;

/**
 * @author madon
 */
public class MoviesAdapter extends RecyclerView.Adapter<FavouriteMoviesAdapter.MovieViewHolder> {
    private final MainActivity mParentActivity;
    private final Movies mMovies;
    private final boolean mTwoPane;
    private Call<Movie> callRequest;

    public MoviesAdapter(MainActivity parent, Movies movies, boolean twoPane) {
        this.mParentActivity = parent;
        this.mMovies = movies;
        this.mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public FavouriteMoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new FavouriteMoviesAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteMoviesAdapter.MovieViewHolder holder, int position) {
        final int pos = position;
        holder.mIvMovie.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(mParentActivity.getApplicationContext())
                        .load(ImageUtils.buildPosterImageUrl(mMovies.getResults().get(pos).getPosterPath(), holder.mIvMovie.getWidth()))
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading)
                        .into(holder.mIvMovie);
            }
        });

        holder.itemView.setTag(mMovies.getResults().get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callRequest != null) {
                    callRequest.cancel();
                }

                getMovieAndShowDetails((int) view.getTag(), holder);
            }
        });

        if (position == 0 && mTwoPane) {
            holder.itemView.callOnClick();
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.getResults().size();
    }

    public void updateMovies(Movies movies) {
        int position = this.mMovies.getResults().size() + 1;
        this.mMovies.appendMovies(movies);
        notifyItemRangeInserted(position, movies.getResults().size());
    }

    private void getMovieAndShowDetails(final int movieId, final FavouriteMoviesAdapter.MovieViewHolder movieViewHolder) {
        final Context context = mParentActivity;

        if (Network.isNetworkAvailable(context)) {

            movieViewHolder.showProgress(true);
            callRequest = MoviesApiManager.getInstance().getMovie(movieId, new DBUpdateListener<Movie>() {
                @Override
                public void onResponse(Movie result) {

                    if (result != null) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelable(MovieFragment.EXTRA_MOVIE_KEY, result);
                            MovieFragment fragment = new MovieFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.movieDetailContainer, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, MovieListDetail.class);
                            intent.putExtra(MovieFragment.EXTRA_MOVIE_KEY, result);

                            context.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
        }

    }
}



