package com.kenzie.appserver.service.model;

import java.util.Objects;

public class HealthMetrics {
    private String userId;
    private double weight;
    private WeightUnit weightUnit;
    private double totalCalorieIntake;
    private double totalCalorieExpenditure;
    private double carbs;
    private double fats;
    private double protein;

    public HealthMetrics() {}

    public HealthMetrics(String userId,
                         double weight,
                         WeightUnit weightUnit,
                         double totalCalorieIntake,
                         double totalCalorieExpenditure,
                         double carbs,
                         double fats,
                         double protein) {
        this.userId = userId;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.totalCalorieIntake = totalCalorieIntake;
        this.totalCalorieExpenditure = totalCalorieExpenditure;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
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

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthMetrics that = (HealthMetrics) o;
        return Double.compare(weight, that.weight) == 0 &&
                Double.compare(totalCalorieIntake, that.totalCalorieIntake) == 0 &&
                Double.compare(totalCalorieExpenditure, that.totalCalorieExpenditure) == 0 &&
                Double.compare(carbs, that.carbs) == 0 &&
                Double.compare(fats, that.fats) == 0 &&
                Double.compare(protein, that.protein) == 0 &&
                Objects.equals(userId, that.userId) &&
                weightUnit == that.weightUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, weight, weightUnit, totalCalorieIntake, totalCalorieExpenditure, carbs, fats, protein);
    }
}
