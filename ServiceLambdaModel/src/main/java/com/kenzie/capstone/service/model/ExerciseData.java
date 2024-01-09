package com.kenzie.capstone.service.model;

import java.util.Objects;

public class ExerciseData {
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

    public ExerciseData() {}

    public ExerciseData(String exerciseId,
                        String type,
                        String intensity,
                        String exerciseName,
                        int duration,
                        int reps,
                        int sets,
                        double distance,
                        double METS,
                        String description){
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

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMETS() {
        return METS;
    }

    public void setMETS(double METS) {
        this.METS = METS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseData)) return false;
        ExerciseData that = (ExerciseData) o;
        return getExerciseId().equals(that.getExerciseId()) && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExerciseId(), getType());
    }
}
