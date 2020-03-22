package com.vladimirkomlev.workoutdiaryandroid.activity.reset;

public interface ResetPasswordView {
    void onResetSuccess(String message);
    void onResetFailed(String message);
    void showProgress();
    void hideProgress();
}
