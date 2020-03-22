package com.vladimirkomlev.workoutdiaryandroid.activity.setup;

import androidx.annotation.NonNull;

import com.vladimirkomlev.workoutdiaryandroid.api.ApiClient;
import com.vladimirkomlev.workoutdiaryandroid.api.PasswordService;
import com.vladimirkomlev.workoutdiaryandroid.model.SetupPasswordRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SetupPasswordPresenter {
    private SetupPasswordView view;

    public SetupPasswordPresenter(SetupPasswordView view) {
        this.view = view;
    }

    void submit(String code, String password) {
        view.showProgress();

        Retrofit retrofit = ApiClient.getInstance();
        PasswordService passwordService = retrofit.create(PasswordService.class);

        SetupPasswordRequest setupPasswordRequest = new SetupPasswordRequest(code, password);

        Call<ResponseBody> responseCall = passwordService.setupPassword(setupPasswordRequest);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                view.showProgress();
                if (response.isSuccessful()) {
                    view.onSetupSuccess("Please sign in");
                } else {
                    view.onSetupFailed("Invalid confirmation code");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                view.onSetupFailed(t.getLocalizedMessage());
            }
        });
    }
}
