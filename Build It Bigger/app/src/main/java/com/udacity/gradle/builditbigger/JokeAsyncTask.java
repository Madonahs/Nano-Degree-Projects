package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class JokeAsyncTask extends AsyncTask<MainActivityFragment, Void, String> {

    private static MyApi myApi = null;
    private MainActivityFragment fragment;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private static final String LOCALHOST_IP_ADDRESS = "http://10.0.2.2:8080/_ah/api/";

    @Override
    protected String doInBackground(MainActivityFragment... params) {
        if(myApi == null) {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)

                .setRootUrl(LOCALHOST_IP_ADDRESS)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });

        myApi = builder.build();
    }

        fragment = params[0];
        context = fragment.getActivity();

        try {
            return myApi.showJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        fragment.loadedJoke = result;
        fragment.launchDisplayJokeActivity();
    }
}