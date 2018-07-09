package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.myandroidlibrary.JokeDisplayActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    ProgressBar progressBar = null;
    public String loadedJoke = null;

    public boolean testFlag = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Set onClickListener for the button
        Button button = root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loadJokes();
            }
        });

        progressBar = root.findViewById(R.id.joke_progressbar);
        progressBar.setVisibility(View.GONE);

        return root;
    }
    public void tellJoke(){
        new EndpointsAsyncTask().execute(this);
    }

    public void launchDisplayJokeActivity(){
        if (!testFlag) {
            Context context = getActivity();
            Intent intent = new Intent(context, JokeDisplayActivity.class);
            assert context != null;
            intent.putExtra(context.getString(R.string.jokeEnvelope), loadedJoke);
            context.startActivity(intent);
            progressBar.setVisibility(View.GONE);
        }

    }
}
