package com.vladimirkomlev.workoutdiaryandroid.activity.signin;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.AuthService;
import com.vladimirkomlev.workoutdiaryandroid.model.AuthRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInPresenter {
    private SignInView view;

    public SignInPresenter(SignInView view) {
        this.view = view;
    }

    void submit(String email, String password) {
        view.showProgress();

        Retrofit apiClient = ApiClient.getInstance();
        AuthService authService = apiClient.create(AuthService.class);

        AuthRequest authRequest = new AuthRequest(email, password);

        Call<AuthResponse> authResponseCall = authService.getAuthToken(authRequest);
        authResponseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onLogInSuccess("You are successfully logged in", response.body());
                } else {
                    view.onLogInFailed("Invalid email or password");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onLogInFailed(t.getLocalizedMessage());
            }
        });
    }
}
