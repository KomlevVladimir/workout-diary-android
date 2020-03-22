package com.vladimirkomlev.workoutdiaryandroid.api;

import com.vladimirkomlev.workoutdiaryandroid.model.ResetPasswordRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.SetupPasswordRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PasswordService {

    @Headers("Content-Type: application/json")
    @POST("reset-password")
    Call<ResponseBody> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @Headers("Content-Type: application/json")
    @POST("setup-password")
    Call<ResponseBody> setupPassword(@Body SetupPasswordRequest setupPasswordRequest);
}
