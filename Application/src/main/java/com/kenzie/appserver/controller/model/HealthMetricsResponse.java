package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthMetricsResponse {
    @NotEmpty
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("weight")
    private double weight;
    @JsonProperty("totalCalorieIntake")
    private double totalCalorieIntake;
    @JsonProperty("totalCalorieExpenditure")
    private double totalCalorieExpenditure;
    @JsonProperty("carbs")
    private double carbs;
    @JsonProperty("fats")
    private double fats;
    @JsonProperty("protein")
    private double protein;

    public HealthMetricsResponse() {}

    public HealthMetricsResponse(HealthMetricsRecord healthMetricsRecord) {
        this.userId = healthMetricsRecord.getUserId();
        this.weight = healthMetricsRecord.getWeight();
        this.totalCalorieIntake = healthMetricsRecord.getTotalCalorieIntake();
        this.totalCalorieExpenditure = healthMetricsRecord.getTotalCalorieExpenditure();
        this.carbs = healthMetricsRecord.getCarbs();
        this.fats = healthMetricsRecord.getFats();
        this.protein = healthMetricsRecord.getProtein();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTotalCalorieIntake() {
        return totalCalorieIntake;
    }

    public void setTotalCalorieIntake(double totalCalorieIntake) {
        this.totalCalorieIntake = totalCalorieIntake;
    }

    public double getTotalCalorieExpenditure() {
        return totalCalorieExpenditure;
    }

    public void setTotalCalorieExpenditure(double totalCalorieExpenditure) {
        this.totalCalorieExpenditure = totalCalorieExpenditure;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
