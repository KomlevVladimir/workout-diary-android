package com.vladimirkomlev.workoutdiaryandroid.api;

import com.vladimirkomlev.workoutdiaryandroid.model.ConfirmationRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.UserRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrationService {

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<UserResponse> signUp(@Body UserRequest userRequest);

    @Headers("Content-Type: application/json")
    @POST("confirm")
    Call<UserResponse> confirm(@Body ConfirmationRequest confirmationRequest);
}
