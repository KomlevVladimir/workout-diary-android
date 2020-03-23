package com.vladimirkomlev.workoutdiaryandroid.model;

public class ConfirmationRequest {
    private String code;

    public ConfirmationRequest() {

    }

    public ConfirmationRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
