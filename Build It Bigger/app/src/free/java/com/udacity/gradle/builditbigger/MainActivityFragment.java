package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.madonasyombua.myandroidlibrary.DisplayJokesActivity;

import java.util.Objects;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //required empty constructor
    public MainActivityFragment() {
    }

    private ProgressBar progressBar = null;
    public String loadedJoke = null;

    public boolean testFlag = false;
    private PublisherInterstitialAd interstitialAd = null;
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);

        interstitialAd = new PublisherInterstitialAd(Objects.requireNonNull(getContext()));
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //process the joke Request
                progressBar.setVisibility(View.VISIBLE);
                tellJoke();

                //pre-fetch the next ad
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.i(LOG_TAG, "Failing");

                //prefetch the next ad
                requestNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                Log.i(LOG_TAG, "Loading");
                super.onAdLoaded();
            }
        });

        requestNewInterstitial();


        Button button = root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    tellJoke();
                }
            }
        });

        progressBar = root.findViewById(R.id.joke_progressbar);
        progressBar.setVisibility(View.GONE);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    public void tellJoke(){
        new JokeAsyncTask().execute(this);
    }

    public void launchDisplayJokeActivity(){
        if (!testFlag) {
            Context context = getActivity();
            Intent intent = new Intent(context, DisplayJokesActivity.class);
            assert context != null;
            intent.putExtra(context.getString(R.string.joke), loadedJoke);
            context.startActivity(intent);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
    }
}
