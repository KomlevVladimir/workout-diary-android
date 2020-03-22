package com.vladimirkomlev.workoutdiaryandroid.model;

public class WorkoutResponse {
    private long id;
    private String title;
    private String date;
    private String description;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public WorkoutResponse(long id, String title, String date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }
}
