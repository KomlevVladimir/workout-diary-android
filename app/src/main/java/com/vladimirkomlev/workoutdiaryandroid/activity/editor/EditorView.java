package com.vladimirkomlev.workoutdiaryandroid.activity.editor;

public interface EditorView {
    void showProgress();
    void hideProgress();
    void onRequestSuccess(String message);
    void onRequestFailed(String message);

}
