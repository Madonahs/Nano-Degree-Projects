package com.madonasyombua.myapplication.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.madonasyombua.myapplication.Helpers.MovieHelper;
import com.madonasyombua.myapplication.Model.Movie;
import com.madonasyombua.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListDetail extends AppCompatActivity {

    private static final String TAG = MovieListDetail.class.getName();


    @BindView(R.id.textview_original_title)
    TextView originalTitle;
    @BindView(R.id.textview_overview)
    TextView tvOverview;
    @BindView(R.id.textview_vote_average)
    TextView voteAverage;
    @BindView(R.id.textview_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.imageview_poster)
    ImageView imageViewPoster;
    @BindView(R.id.textview_release_date_title)
    TextView releaseDateTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie_parcel));

        float vote = (float) (movie.getMovieVote() / 2);
        AppCompatRatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setIsIndicator(true);
        ratingBar.setStepSize(0.1f);
        ratingBar.setRating(vote);

        originalTitle.setText(movie.getTitle());
        releaseDateTitle.setText("Release Date");

        Picasso.with(this)
                .load(movie.getMoviePosterPath())
                .resize(187,285)
                .placeholder(R.drawable.loading)
                .error(R.drawable.alert_circle_outline)
                .into(imageViewPoster);

        String overView = movie.getMovieOverview();

        if(overView == null){
            tvOverview.setTypeface(null, Typeface.BOLD_ITALIC);
            overView = getResources().getString(R.string.error);
        }

        tvOverview.setText(overView);
        voteAverage.setText(movie.getVoteAverage());

        String releaseDate = movie.getMovieRelease();
        if(releaseDate != null){

            try{
                releaseDate = MovieHelper.LocalizedDate(this, releaseDate,movie.getDate());
            }catch (ParseException pe){
                Log.e(TAG,String.valueOf(R.string.release_date_error),pe);
            }
        }else {

            releaseDate = getResources().getString(R.string.no_date_found);

        }

        tvReleaseDate.setText(releaseDate);

    }


}
