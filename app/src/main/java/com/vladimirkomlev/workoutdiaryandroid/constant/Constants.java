package com.vladimirkomlev.workoutdiaryandroid.constant;

public class Constants {
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";
    public static final String PASSWORD_PATTERN =
            ("^" +
                    "(?=.*\\d)" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?!.*\\s)" +
                    ".{8,100}$");
    public static final String NOT_EMPTY_PATTERN = "^\\s*\\S[\\s\\S]*$";
    public static final String ONLY_DIGITS_PATTERN = "\\d+";
    public static final String PREFERENCES = "myPrefs";
}
