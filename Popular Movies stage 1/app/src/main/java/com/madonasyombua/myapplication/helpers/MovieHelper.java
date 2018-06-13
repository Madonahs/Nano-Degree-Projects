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
