package com.vladimirkomlev.workoutdiaryandroid.api;

import com.vladimirkomlev.workoutdiaryandroid.model.AuthRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("token")
    Call<AuthResponse> getAuthToken(@Body AuthRequest authRequest);
}
