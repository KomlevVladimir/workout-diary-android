package com.vladimirkomlev.workoutdiaryandroid.activity.confirm;

public interface ConfirmEmailView {
    void onConfirmSuccess(String message);
    void onConfirmFailed(String message);
    void showProgress();
    void hideProgress();
}
