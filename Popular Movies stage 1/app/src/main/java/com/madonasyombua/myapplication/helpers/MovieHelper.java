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
package com.madonasyombua.myapplication.helpers;


import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author madon
 *
 */
public class MovieHelper {
//this class will help me in the date conversion
    /**
     *
     * @param date this is the date to be formatted
     * @param format the format
     * @return date
     * @throws ParseException
     */
    private static Date getDate(String date,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.parse(date);

    }

    /**
     *
     * @param context app
     * @param date date to be formatted
     * @param format the format
     * @return convert date to a string
     * @throws ParseException
     */
    public static String LocalizedDate(Context context,String date,String format) throws ParseException {

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        return dateFormat.format(getDate(date, format));
    }

}
