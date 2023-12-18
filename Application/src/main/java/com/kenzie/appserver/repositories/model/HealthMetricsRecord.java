package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "HealthMetrics")
public class HealthMetricsRecord {
    private String userId;
    private double weight;
    private double totalCalorieIntake;
    private double totalCalorieExpenditure;
    private double carbs;
    private double fats;
    private double protein;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "weight")
    public double getWeight() {
        return weight;
    }

    @DynamoDBAttribute(attributeName = "totalCalorieIntake")
    public double getTotalCalorieIntake() {
        return totalCalorieIntake;
    }

    @DynamoDBAttribute(attributeName = "totalCalorieExpenditure")
    public double getTotalCalorieExpenditure() {
        return totalCalorieExpenditure;
    }

    @DynamoDBAttribute(attributeName = "carbs")
    public double getCarbs() {
        return carbs;
    }

    @DynamoDBAttribute(attributeName = "fats")
    public double getFats() {
        return fats;
    }

    @DynamoDBAttribute(attributeName = "protein")
    public double getProtein() {
        return protein;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setTotalCalorieIntake(double totalCalorieIntake) {
        this.totalCalorieIntake = totalCalorieIntake;
    }

    public void setTotalCalorieExpenditure(double totalCalorieExpenditure) {
        this.totalCalorieExpenditure = totalCalorieExpenditure;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthMetricsRecord that = (HealthMetricsRecord) o;
        return Double.compare(weight, that.weight) == 0 &&
                Double.compare(totalCalorieIntake, that.totalCalorieIntake) == 0 &&
                Double.compare(totalCalorieExpenditure, that.totalCalorieExpenditure) == 0 &&
                Double.compare(carbs, that.carbs) == 0 &&
                Double.compare(fats, that.fats) == 0 &&
                Double.compare(protein, that.protein) == 0 &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, weight, totalCalorieIntake, totalCalorieExpenditure, carbs, fats, protein);
    }
}
