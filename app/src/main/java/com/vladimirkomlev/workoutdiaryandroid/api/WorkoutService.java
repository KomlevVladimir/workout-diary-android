package com.vladimirkomlev.workoutdiaryandroid.api;

import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutRequest;
import com.vladimirkomlev.workoutdiaryandroid.model.WorkoutResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WorkoutService {

    @Headers("Content-Type: application/json")
    @GET("/users/{userId}/workouts")
    Call<List<WorkoutResponse>> getWorkouts(
            @Path("userId") long userId,
            @Header("Authorization") String token
    );

    @Headers("Content-Type: application/json")
    @POST("/users/{userId}/workouts")
    Call<WorkoutResponse> createWorkout(
            @Path("userId") long userId,
            @Header("Authorization") String token,
            @Body WorkoutRequest workoutRequest
    );

    @Headers("Content-Type: application/json")
    @PUT("/users/{userId}/workouts/{workoutId}")
    Call<WorkoutResponse> updateWorkout(
            @Path("userId") long userId,
            @Path("workoutId") long workoutId,
            @Header("Authorization") String token,
            @Body WorkoutRequest workoutRequest
    );

    @Headers("Content-Type: application/json")
    @DELETE("/users/{userId}/workouts/{workoutId}")
    Call<ResponseBody> deleteWorkout(
            @Path("userId") long userId,
            @Path("workoutId") long workoutId,
            @Header("Authorization") String token
    );
}
