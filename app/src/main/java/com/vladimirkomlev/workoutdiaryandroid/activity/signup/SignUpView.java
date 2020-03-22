package com.vladimirkomlev.workoutdiaryandroid.activity.signup;

import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;

public interface SignUpView {
    void onRegisterSuccess(String message, UserResponse userResponse);
    void onRegisterFailed(String message);
    void showProgress();
    void hideProgress();
}
