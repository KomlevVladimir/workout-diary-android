package com.vladimirkomlev.workoutdiaryandroid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


    public static String toFirstFormat(String string) {
        String formattedDate = null;
        try {
            Date date = sdf2.parse(string);
            formattedDate = sdf1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String toSecondFormat(String string) {
        String formattedDate = null;
        try {
            Date date = sdf1.parse(string);
            formattedDate = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
