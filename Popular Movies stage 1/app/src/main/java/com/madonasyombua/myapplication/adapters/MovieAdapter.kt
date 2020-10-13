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
package com.madonasyombua.myapplication.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.madonasyombua.myapplication.R
import com.madonasyombua.myapplication.model.Movie
import com.squareup.picasso.Picasso

/**
 * @author madon
 */
class MovieAdapter
/**
 * my constructor takes two parameters
 *
 *
 * @param context the context
 * @param movies movies from my model movie
 */(var mContext: Context, var mMovies: Array<Movie?>) : BaseAdapter() {
    /**
     * I will check if movie is = to null or the length is = to 0 and return - 1 else return the length
     * @return movies.length
     */
    override fun getCount(): Int {
        return if (mMovies == null || mMovies?.size == 0) {
            -1
        } else mMovies!!.size
    }

    /**
     *
     * @param position i will try to get the position
     * @return position
     */
    override fun getItem(position: Int): Any? {
        return if (mMovies == null || mMovies?.isEmpty()) {
            null
        } else mMovies!![position]
    }

    /**
     *
     * @param position item id position
     * @return 0
     */
    override fun getItemId(position: Int): Long {
        return 0
    }

    /**
     * As recommended
     * Using Picasso To Fetch Images and Load Them Into Views
     * You can use Picasso to easily load album art thumbnails into your views using:
     *
     *
     * Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
     * Picasso will handle loading the images on a background thread, image decompression and caching the images.
     * @param position the position
     * @param convertView the view
     * @param parent parents
     * @return picasso ImageView
     */
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val movieImageView: ImageView = convertView as ImageView
        Picasso.with(mContext)
                .load(mMovies?.get(position)?.getMoviePosterPath())
                .resize(278, 350)
                .error(R.drawable.alert_circle_outline) //when we get an error
                .placeholder(R.drawable.loading) // as the image loads
                .into(movieImageView) // the target
        return movieImageView
    }
}