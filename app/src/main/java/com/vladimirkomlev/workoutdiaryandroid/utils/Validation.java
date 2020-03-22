package com.vladimirkomlev.workoutdiaryandroid.utils;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.EMAIL_PATTERN;
import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.NOT_EMPTY_PATTERN;
import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.ONLY_DIGITS_PATTERN;
import static com.vladimirkomlev.workoutdiaryandroid.constant.Constants.PASSWORD_PATTERN;

public class Validation {

    public static boolean validateEmail(MaterialEditText email) {
        return email.validateWith(new RegexpValidator(
                "Please enter a valid email address",
                EMAIL_PATTERN)
        );
    }

    public static boolean validatePassword(MaterialEditText password) {
        return password.validateWith(new RegexpValidator(
                "Password must have at least 8 characters" +
                        " and include uppercase and lowercase letters and numbers",
                PASSWORD_PATTERN)
        );
    }

    public static boolean validateNotEmptyField(MaterialEditText field) {
        return field.validateWith(new RegexpValidator(
                "Field cannot be empty",
                NOT_EMPTY_PATTERN)
        );
    }

    public static boolean validateAge(MaterialEditText age) {
        return age.validateWith(new RegexpValidator(
                "Please enter a valid age",
                ONLY_DIGITS_PATTERN)
        );
    }
}
