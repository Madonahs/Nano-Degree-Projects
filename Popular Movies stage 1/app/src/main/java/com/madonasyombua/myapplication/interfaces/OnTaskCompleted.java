package com.madonasyombua.myapplication.interfaces;

import com.madonasyombua.myapplication.model.Movie;

/**
 * @author madon
  */

public interface OnTaskCompleted {
    void onFetchMovie(Movie [] movies);
}
