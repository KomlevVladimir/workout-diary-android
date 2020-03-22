package com.vladimirkomlev.workoutdiaryandroid.activity.signup;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.RegistrationService;
import com.vladimirkomlev.workoutdiaryandroid.model.UserRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpPresenter {
    private SignUpView view;

    public SignUpPresenter(SignUpView view) {
        this.view = view;
    }

    void submit(String firstname, String lastname, int age, String email, String password) {
        view.showProgress();

        Retrofit retrofit = ApiClient.getInstance();
        RegistrationService registrationService = retrofit.create(RegistrationService.class);

        UserRequest request = new UserRequest(firstname, lastname, age, email, password);

        Call<UserResponse> userResponseCall = registrationService.signUp(request);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onRegisterSuccess("Please confirm your email", response.body());

                } else {
                    view.onRegisterFailed("Registration failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRegisterFailed(t.getLocalizedMessage());
            }
        });
    }
}
