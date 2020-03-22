package com.vladimirkomlev.workoutdiaryandroid.activity.confirm;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.RegistrationService;
import com.vladimirkomlev.workoutdiaryandroid.model.ConfirmationRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmEmailPresenter {
    private ConfirmEmailView view;

    public ConfirmEmailPresenter(ConfirmEmailView view) {
        this.view = view;
    }

    void submit(String code) {
        view.showProgress();

        Retrofit retrofit = ApiClient.getInstance();
        RegistrationService registrationService = retrofit.create(RegistrationService.class);

        ConfirmationRequest confirmationRequest = new ConfirmationRequest(code);

        Call<UserResponse> userResponseCall = registrationService.confirm(confirmationRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                view.hideProgress();
                if (response.isSuccessful()) {
                    view.onConfirmSuccess("Your account has been confirmed. Please sign in.");
                } else {
                    view.onConfirmFailed("Invalid confirmation code");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onConfirmFailed(t.getLocalizedMessage());
            }
        });
    }
}
