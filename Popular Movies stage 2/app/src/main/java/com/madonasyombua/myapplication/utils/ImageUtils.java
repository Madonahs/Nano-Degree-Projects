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
package com.madonasyombua.myapplication.utils;


/**
 * @author madona syombua
 *
 */
public class ImageUtils {
    private ImageUtils() {
    }

    /**
     * the back drop image url
     * @param backdropPath the image
     * @param holderWidth the width
     * @return imageURL + image width + backdrop
     */
    public static String buildBackdropImage(String backdropPath, double holderWidth) {

        String imageWidth;

        if (holderWidth > 780) {
            imageWidth = "original";
        } else if (holderWidth > 500) {
            imageWidth = "w780";
        } else if (holderWidth > 342) {
            imageWidth = "w500";
        } else if (holderWidth > 185) {
            imageWidth = "w342";
        } else if (holderWidth > 154) {
            imageWidth = "w185";
        } else if (holderWidth > 92) {
            imageWidth = "w154";
        } else {
            imageWidth = "w92";
        }


        return Constants.TMDB_IMAGE_URL
                + imageWidth
                + "/"
                + backdropPath;
    }

    /**
     * the poster image URI
     * @param posterPath poster path
     * @param holderWidth the width we want to display
     * @return the path from constant + the width + poster path
     */
    public static String buildPosterImageUrl(String posterPath, double holderWidth) {
        String imageWidth;


        if (holderWidth > 500) {
            imageWidth = "w342";
        } else {
            imageWidth = "w185";
        }

        return Constants.TMDB_IMAGE_URL
                + imageWidth
                + "/"
                + posterPath;
    }
}
