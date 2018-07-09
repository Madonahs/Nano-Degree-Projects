package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.madonasyombua.jokelib.Jokes;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a joke */
    @ApiMethod(name = "showJoke")
    public MyBean showJoke() {
        MyBean response = new MyBean();
        Jokes jokeProvider = new Jokes();
        response.setData(jokeProvider.getJoke());

        return response;
    }
}


