package com.muliamaulana.cataloguemovie.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by muliamaulana on 14/04/18.
 */

public class DateTimeFormat {
    private static String formatDate(String date, String format) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getShortDate(String date) {
        return formatDate(date, "d MMMM yyyy");
    }

    public static String getLongDate(String date) {
        return formatDate(date, "EEEE, d MMMM yyyy");
    }

    public static String getYear(String year){
        return formatDate(year, "yyyy");
    }
}
