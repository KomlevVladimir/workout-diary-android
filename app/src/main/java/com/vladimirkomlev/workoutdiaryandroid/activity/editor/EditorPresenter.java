package com.vladimirkomlev.workoutdiaryandroid.activity.editor;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.WorkoutService;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditorPresenter {
    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    void saveWorkout(long userId, String token, String title, String date, String description) {
        view.showProgress();

        Retrofit apiClient = ApiClient.getInstance();
        WorkoutService workoutService = apiClient.create(WorkoutService.class);

        WorkoutRequest workoutRequest = new WorkoutRequest(title, date, description);

        Call<WorkoutResponse> workoutResponseCall = workoutService.createWorkout(userId, "Bearer " + token, workoutRequest);
        workoutResponseCall.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorkoutResponse> call, @NonNull Response<WorkoutResponse> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onRequestSuccess("Workout has been created");
                } else {
                    view.onRequestFailed("Could not create workout");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WorkoutResponse> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestFailed(t.getLocalizedMessage());
            }
        });
    }

    void updateWorkout(long userId, long workoutId, String token, String title, String date, String description) {
        view.showProgress();

        Retrofit apiClient = ApiClient.getInstance();
        WorkoutService workoutService = apiClient.create(WorkoutService.class);

        WorkoutRequest workoutRequest = new WorkoutRequest(title, date, description);

        Call<WorkoutResponse> workoutResponseCall =
                workoutService.updateWorkout(userId, workoutId, "Bearer " + token, workoutRequest);
        workoutResponseCall.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorkoutResponse> call, @NonNull Response<WorkoutResponse> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    view.onRequestSuccess("Workout has been updated");
                } else {
                    view.onRequestFailed("Could not update workout");
                }

            }

            @Override
            public void onFailure(@NonNull Call<WorkoutResponse> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestFailed(t.getLocalizedMessage());
            }
        });
    }

    void deleteWorkout(long userId, long workoutId, String token) {
        view.showProgress();

        Retrofit apiClient = ApiClient.getInstance();
        WorkoutService workoutService = apiClient.create(WorkoutService.class);

        Call<ResponseBody> workoutResponseCall =
                workoutService.deleteWorkout(userId, workoutId, "Bearer " + token);
        workoutResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                view.hideProgress();

                if (response.isSuccessful()) {
                    view.onRequestSuccess("Workout has been deleted");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestFailed("Could not delete workout");
            }
        });

    }
}
