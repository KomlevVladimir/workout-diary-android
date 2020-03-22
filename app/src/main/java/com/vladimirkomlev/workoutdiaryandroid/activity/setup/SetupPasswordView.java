package com.vladimirkomlev.workoutdiaryandroid.activity.setup;

public interface SetupPasswordView {
    void onSetupSuccess(String message);
    void onSetupFailed(String message);
    void showProgress();
    void hideProgress();
}
