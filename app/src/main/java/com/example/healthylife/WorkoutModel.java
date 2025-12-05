package com.example.healthylife;

public class WorkoutModel {
    private String title;
    private String duration;
    private String reps;
    private int image;

    public WorkoutModel(String title, String duration, String reps, int image) {
        this.title = title;
        this.duration = duration;
        this.reps = reps;
        this.image = image;
    }

    public String getTitle() { return title; }
    public String getDuration() { return duration; }
    public String getReps() { return reps; }
    public int getImage() { return image; }
}
