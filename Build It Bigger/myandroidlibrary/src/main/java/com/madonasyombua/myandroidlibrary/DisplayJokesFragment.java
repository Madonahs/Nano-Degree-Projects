package com.madonasyombua.myandroidlibrary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayJokesFragment extends Fragment {

    public static final String EXTRA_JOKE = "joke";
    //empty constructor
    public DisplayJokesFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);

        String joke;
        Intent intent = getActivity().getIntent();
        joke = intent.getStringExtra(EXTRA_JOKE);

        TextView jokeTextView = view.findViewById(R.id.tv_joke);
        if (!TextUtils.isEmpty(joke)) {
            jokeTextView.setText(joke);
        }else {
            jokeTextView.setText(R.string.have_fun);
        }

        return view;
    }
}