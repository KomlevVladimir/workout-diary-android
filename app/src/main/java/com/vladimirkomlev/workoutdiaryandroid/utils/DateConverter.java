package com.vladimirkomlev.workoutdiaryandroid.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    private static DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private static DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String toFirstFormat(String string) {
        LocalDate localDate = LocalDate.parse(string, format2);
        return format1.format(localDate);
    }

    public static String toSecondFormat(String string) {
        LocalDate localDate = LocalDate.parse(string, format1);
        return format2.format(localDate);
    }
}
