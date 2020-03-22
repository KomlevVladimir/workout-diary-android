package com.vladimirkomlev.workoutdiaryandroid.model;

public class ConfirmationRequest {
    private String secret;

    public ConfirmationRequest() {

    }

    public ConfirmationRequest(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
