package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;
@DynamoDBTable(tableName = "Exercises")
public class ExerciseRecord {

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
    @DynamoDBHashKey(attributeName = "exerciseId")
    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "TypeIndex", attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @DynamoDBAttribute(attributeName = "intensity")
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }
    @DynamoDBAttribute(attributeName = "exerciseName")
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
    @DynamoDBAttribute(attributeName = "duration" )
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    @DynamoDBAttribute(attributeName = "reps")
    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
    @DynamoDBAttribute(attributeName = "sets")
    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
    @DynamoDBAttribute(attributeName = "distance")
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    @DynamoDBAttribute(attributeName = "METS")
    public double getMETS() {
        return METS;
    }

    public void setMETS(double METS) {
        this.METS = METS;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseRecord)) return false;
        ExerciseRecord that = (ExerciseRecord) o;
        return getExerciseId().equals(that.getExerciseId()) && getExerciseName().equals(that.getExerciseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExerciseId(), getExerciseName());
    }
}
