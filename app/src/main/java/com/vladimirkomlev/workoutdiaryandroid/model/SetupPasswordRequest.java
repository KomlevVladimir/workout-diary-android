package com.vladimirkomlev.workoutdiaryandroid.model;

public class SetupPasswordRequest {
    private String secret;
    private String password;

    public SetupPasswordRequest() {

    }

    public SetupPasswordRequest(String secret, String password) {
        this.secret = secret;
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
