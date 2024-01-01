package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.kenzie.appserver.service.model.WeightUnit;

import java.util.Objects;

@DynamoDBTable(tableName = "HealthMetrics")
public class HealthMetricsRecord {
    private String userId;
    private Double weight;
    private WeightUnit weightUnit;
    private Double totalCalorieIntake;
    private Double totalCalorieExpenditure;
    private Double carbs;
    private Double fats;
    private Double protein;

    public HealthMetricsRecord() {}

    public HealthMetricsRecord(String userId) {
        this.userId = userId;
        this.weight = 0.0;
        this.weightUnit = WeightUnit.KG;
        this.totalCalorieIntake = 0.0;
        this.totalCalorieExpenditure = 0.0;
        this.carbs = 0.0;
        this.fats = 0.0;
        this.protein = 0.0;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "weight")
    public Double getWeight() {
        return weight;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "weightUnit")
    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    @DynamoDBAttribute(attributeName = "totalCalorieIntake")
    public Double getTotalCalorieIntake() {
        return totalCalorieIntake;
    }

    @DynamoDBAttribute(attributeName = "totalCalorieExpenditure")
    public Double getTotalCalorieExpenditure() {
        return totalCalorieExpenditure;
    }

    @DynamoDBAttribute(attributeName = "carbs")
    public Double getCarbs() {
        return carbs;
    }

    @DynamoDBAttribute(attributeName = "fats")
    public Double getFats() {
        return fats;
    }

    @DynamoDBAttribute(attributeName = "protein")
    public Double getProtein() {
        return protein;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public void setTotalCalorieIntake(Double totalCalorieIntake) {
        this.totalCalorieIntake = totalCalorieIntake;
    }

    public void setTotalCalorieExpenditure(Double totalCalorieExpenditure) {
        this.totalCalorieExpenditure = totalCalorieExpenditure;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthMetricsRecord that = (HealthMetricsRecord) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(weight, that.weight) &&
                weightUnit == that.weightUnit &&
                Objects.equals(totalCalorieIntake, that.totalCalorieIntake) &&
                Objects.equals(totalCalorieExpenditure, that.totalCalorieExpenditure) &&
                Objects.equals(carbs, that.carbs) &&
                Objects.equals(fats, that.fats) &&
                Objects.equals(protein, that.protein);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, weight, weightUnit, totalCalorieIntake, totalCalorieExpenditure, carbs, fats, protein);
    }
}
