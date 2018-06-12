package com.madonasyombua.myapplication.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.interfaces.DBUpdateListener;
import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.model.MovieReview;
import com.madonasyombua.myapplication.utils.FavMoviesSharedPrefs;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListDetail extends AppCompatActivity implements DBUpdateListener {

    private static final String TAG = MovieListDetail.class.getName();
    @BindView(R.id.coordinate_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.iv_backdrop)
    ImageView mIvBackDrop;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_release)
    TextView mTvReleaseDate;
    @BindView(R.id.tv_rating)
    TextView mTvRating;
    @BindView(R.id.tv_movie_overview)
    TextView mTvOverview;
    @BindView(R.id.iv_poster)
    ImageView mIvPoster;
    @BindView(R.id.fab_favorite)
    FloatingActionButton mButtonFavorite;
    @BindColor(R.color.colorPrimaryDark)
    int primaryDark;
    @BindView(R.id.fab_trailer)
    FloatingActionButton mButtonTrailer;
    @BindView(R.id.fab_share)
    FloatingActionButton mButtonShare;
    @BindView(R.id.review_layout0)
    CardView mReviewLayout0;
    @BindView(R.id.review_layout1)
    CardView mReviewLayout1;

    @BindString(R.string.no_internet_connection)
    String noInternetConnection;
    @BindString(R.string.error)
    String somethingWentWrong;
    @BindString(R.string.movie_trailers_dialog_title)
    String movieTrailerDialogTitle;
    @BindString(R.string.no_internet_connection_to_show_reviews)
    String noInternetConnectionToShowReviews;


    private Movie movie;
    private FavMoviesSharedPrefs moviesSharedPrefs;
    private ProgressBar progressBar;
    private ArrayList<MovieReview> movieReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_detail);
        ButterKnife.bind(this);


    }

    @Override
    public void onSuccess(int operationType) {

    }

    @Override
    public void onFailure() {

    }
}