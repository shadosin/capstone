package com.kenzie.appserver.service.model;

public class Exercise {
    private String exerciseId;

    private String type;

    private String intensity;

    private String exerciseName;

    private int duration;
    private int reps;
    private int sets;
    private double distance;
    private double METS;
    private String description;

    public Exercise(String exerciseId, String type, String intensity, String exerciseName, int duration, int reps,
                    int sets, double distance, double METS, String description){
        this.exerciseId = exerciseId;
        this.type = type;
        this.intensity = intensity;
        this.exerciseName = exerciseName;
        this.duration = duration;
        this.reps = reps;
        this.sets = sets;
        this.distance = distance;
        this.METS = METS;
        this.description = description;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getType() {
        return type;
    }

    public String getIntensity() {
        return intensity;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDuration() {
        return duration;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public double getDistance() {
        return distance;
    }

    public double getMETS() {
        return METS;
    }

    public String getDescription() {
        return description;
    }
}
