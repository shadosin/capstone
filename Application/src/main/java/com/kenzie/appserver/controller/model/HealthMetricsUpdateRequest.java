package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.WeightUnit;

import javax.validation.constraints.NotEmpty;

public class HealthMetricsUpdateRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("weightUnit")
    private WeightUnit weightUnit;
    @JsonProperty("totalCalorieIntake")
    private Double totalCalorieIntake;
    @JsonProperty("totalCalorieExpenditure")
    private Double totalCalorieExpenditure;
    @JsonProperty("carbs")
    private Double carbs;
    @JsonProperty("fats")
    private Double fats;
    @JsonProperty("protein")
    private Double protein;

    public HealthMetricsUpdateRequest() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getTotalCalorieIntake() {
        return totalCalorieIntake;
    }

    public void setTotalCalorieIntake(Double totalCalorieIntake) {
        this.totalCalorieIntake = totalCalorieIntake;
    }

    public Double getTotalCalorieExpenditure() {
        return totalCalorieExpenditure;
    }

    public void setTotalCalorieExpenditure(Double totalCalorieExpenditure) {
        this.totalCalorieExpenditure = totalCalorieExpenditure;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }
}
