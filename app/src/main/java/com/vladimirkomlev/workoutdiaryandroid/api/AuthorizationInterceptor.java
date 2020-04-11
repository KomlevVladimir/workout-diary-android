package com.vladimirkomlev.workoutdiaryandroid.api;

import android.content.Intent;

import com.vladimirkomlev.workoutdiaryandroid.App;
import com.vladimirkomlev.workoutdiaryandroid.activity.signin.SignInActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 401 || response.code() == 403) {
            Intent intent = new Intent(App.context, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            App.context.startActivity(intent);
        }
        return response;
    }
}
