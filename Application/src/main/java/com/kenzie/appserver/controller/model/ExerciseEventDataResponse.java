package com.kenzie.appserver.controller.model;

import com.kenzie.capstone.service.model.ExerciseData;

import java.util.Objects;

public class ExerciseEventDataResponse {

    private String exerciseId;
    private String type;
    private String intensity;
    private String name;
    private int duration;
    private int reps;
    private int sets;
    private double distance;
    private double METS;
    private String description;

    public ExerciseEventDataResponse() {

    }

    public ExerciseEventDataResponse(ExerciseData exerciseData) {
        this.exerciseId = exerciseData.getExerciseId();
        this.type = exerciseData.getType();
        this.intensity = exerciseData.getIntensity();
        this.name = exerciseData.getExerciseName();
        this.duration = exerciseData.getDuration();
        this.reps = exerciseData.getReps();
        this.sets = exerciseData.getSets();
        this.distance = exerciseData.getDistance();
        this.METS = exerciseData.getMETS();
        this.description = exerciseData.getDescription();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseEventDataResponse that = (ExerciseEventDataResponse) o;
        return duration == that.duration &&
                reps == that.reps &&
                sets == that.sets &&
                Double.compare(distance, that.distance) == 0 &&
                Double.compare(METS, that.METS) == 0 &&
                Objects.equals(exerciseId, that.exerciseId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(intensity, that.intensity) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseId, type, intensity, name, duration, reps, sets, distance, METS, description);
    }
}
