package com.vladimirkomlev.workoutdiaryandroid.activity.workouts;

import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;

import java.util.List;

public interface WorkoutsView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<WorkoutResponse> workouts);
    void onErrorLoading(String message);
}
