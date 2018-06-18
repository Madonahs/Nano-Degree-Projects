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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.madonasyombua.myapplication.model.Movie;
import com.madonasyombua.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * @author madon
 */
public class MovieAdapter extends BaseAdapter {

    public  Movie[] mMovies;
    public Context mContext;

    /**
     * my constructor takes two parameters
     * <p/>
     * @param context the context
     * @param movies movies from my model movie
     */

    public  MovieAdapter(Context context, Movie[] movies){

        mMovies = movies;
        mContext = context;
    }

    /**
     * I will check if movie is = to null or the length is = to 0 and return - 1 else return the length
     * @return movies.length
     */
    @Override
    public int getCount() {

        if(mMovies == null || mMovies.length == 0){
            return -1;
        }
        return mMovies.length;
    }

    /**
     *
     * @param position i will try to get the position
     * @return position
     */
    @Override
    public Object getItem(int position) {
        if(mMovies == null || mMovies.length== 0){
            return  null;
        }
        return mMovies[position];
    }

    /**
     *
     * @param position item id position
     * @return 0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * As recommended
     * Using Picasso To Fetch Images and Load Them Into Views
     * You can use Picasso to easily load album art thumbnails into your views using:
     * <p/>
     * Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
     * Picasso will handle loading the images on a background thread, image decompression and caching the images.
     * @param position the position
     * @param convertView the view
     * @param parent parents
     * @return picasso ImageView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView movieImageView;

        if(convertView == null){
            movieImageView = new ImageView(mContext);
            movieImageView.setAdjustViewBounds(true);
        }else {
            movieImageView = (ImageView) convertView;
        }
        Picasso.with(mContext)
                .load(mMovies[position].getMoviePosterPath())
                .resize(278,350)
                .error(R.drawable.alert_circle_outline)//when we get an error
                .placeholder(R.drawable.loading)// as the image loads
                .into(movieImageView);// the target

        return movieImageView;
    }
}
