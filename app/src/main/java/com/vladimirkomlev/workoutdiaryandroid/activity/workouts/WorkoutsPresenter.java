package com.vladimirkomlev.workoutdiaryandroid.activity.workouts;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.WorkoutService;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkoutsPresenter {
    private WorkoutsView view;

    public WorkoutsPresenter(WorkoutsView view) {
        this.view = view;
    }

    void getData(long userId, String token) {
        view.showLoading();

        Retrofit apiClient = ApiClient.getInstance();
        WorkoutService workoutService = apiClient.create(WorkoutService.class);

        Call<List<WorkoutResponse>> workoutsCall = workoutService.getWorkouts(userId, "Bearer " + token);
        workoutsCall.enqueue(new Callback<List<WorkoutResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<WorkoutResponse>> call, @NonNull Response<List<WorkoutResponse>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<WorkoutResponse>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());

            }
        });

    }


}
