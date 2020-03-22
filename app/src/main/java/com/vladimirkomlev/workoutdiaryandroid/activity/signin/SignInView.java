package com.vladimirkomlev.workoutdiaryandroid.activity.signin;

import com.vladimirkomlev.workoutdiaryandroid.model.AuthResponse;

public interface SignInView {
    void onLogInSuccess(String message, AuthResponse authResponse);
    void onLogInFailed(String message);
    void showProgress();
    void hideProgress();
}
