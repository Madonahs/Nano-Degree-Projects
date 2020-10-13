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
package com.madonasyombua.myapplication.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import butterknife.BindView
import butterknife.ButterKnife
import com.madonasyombua.myapplication.R
import com.madonasyombua.myapplication.helpers.MovieHelper.LocalizedDate
import com.madonasyombua.myapplication.model.Movie
import com.squareup.picasso.Picasso
import java.text.ParseException

class MovieListDetail : AppCompatActivity() {
    @BindView(R.id.textview_original_title)
    var originalTitle: TextView? = null

    @BindView(R.id.textview_overview)
    var tvOverview: TextView? = null

    @BindView(R.id.textview_vote_average)
    var voteAverage: TextView? = null

    @BindView(R.id.textview_release_date)
    var tvReleaseDate: TextView? = null

    @BindView(R.id.imageview_poster)
    var imageViewPoster: ImageView? = null

    @BindView(R.id.textview_release_date_title)
    var releaseDateTitle: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list_detail)
        ButterKnife.bind(this)
        val intent = intent
        val movie: Movie? = intent.getParcelableExtra(getString(R.string.movie_parcel))
        val vote = (movie?.movieVote?.div(2))?.toFloat()
        val ratingBar = findViewById<AppCompatRatingBar>(R.id.rating)
        ratingBar.setIsIndicator(true)
        ratingBar.stepSize = 0.1f
        if (vote != null) {
            ratingBar.rating = vote
        }
        if (movie != null) {
            originalTitle?.text = movie.title
        }
        releaseDateTitle?.text = "Release Date"
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getMoviePosterPath())
                    .resize(187, 285)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.alert_circle_outline)
                    .into(imageViewPoster)
        }
        var overView = movie.movieOverview
        if (overView == null) {
            tvOverview!!.setTypeface(null, Typeface.BOLD_ITALIC)
            overView = resources.getString(R.string.error)
        }
        tvOverview!!.text = overView
        voteAverage!!.text = movie?.voteAverage
        var releaseDate = movie?.movieRelease
        if (releaseDate != null) {
            try {
                releaseDate = LocalizedDate(this, releaseDate, movie.getDate())
            } catch (pe: ParseException) {
                Log.e(TAG, R.string.release_date_error.toString(), pe)
            }
        } else {
            releaseDate = resources.getString(R.string.no_date_found)
        }
        tvReleaseDate!!.text = releaseDate
    }

    companion object {
        private val TAG = MovieListDetail::class.java.name
    }
}