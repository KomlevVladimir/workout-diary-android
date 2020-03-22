package com.vladimirkomlev.workoutdiaryandroid.activity.reset;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.PasswordService;
import com.vladimirkomlev.workoutdiaryandroid.model.ResetPasswordRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPasswordPresenter {
    private ResetPasswordView view;

    public ResetPasswordPresenter(ResetPasswordView view) {
        this.view = view;
    }

    void submit(String email) {
        view.showProgress();

        Retrofit retrofit = ApiClient.getInstance();
        PasswordService passwordService = retrofit.create(PasswordService.class);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(email);

        Call<ResponseBody> responseCall = passwordService.resetPassword(resetPasswordRequest);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                view.hideProgress();
                if (response.isSuccessful()) {
                    view.onResetSuccess("Please setup new password");
                } else {
                    view.onResetFailed("Email not found");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onResetFailed(t.getLocalizedMessage());
            }
        });
    }
}
