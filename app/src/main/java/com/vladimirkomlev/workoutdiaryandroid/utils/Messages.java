package com.vladimirkomlev.workoutdiaryandroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class Messages {

    public static void showError(Context context, String message) {
        Toast errorToast = Toast.makeText(context, message , Toast.LENGTH_LONG);
        TextView errorView = errorToast.getView().findViewById(android.R.id.message);
        errorView.setBackgroundColor(Color.RED);
        errorView.setTextColor(Color.WHITE);
        errorToast.show();
    }

    public static void showSuccess(Context context, String message) {
        Toast.makeText(context, message , Toast.LENGTH_LONG).show();
    }
}
