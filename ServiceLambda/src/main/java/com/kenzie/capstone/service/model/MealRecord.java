package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;
@DynamoDBTable(tableName = "Meals")
public class MealRecord {

    private String mealId;

    private String name;

    private String url;

    private String type;

    private double calories;

    private double protein;

    private double carb;

    private double fat;

    private boolean glutenFree;

    private boolean vegan;
    @DynamoDBHashKey(attributeName = "mealId")
    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "TypeIndex", attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @DynamoDBAttribute(attributeName = "protein")
    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
    @DynamoDBAttribute(attributeName = "carbs")
    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }
    @DynamoDBAttribute(attributeName = "fat")
    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }
    @DynamoDBAttribute(attributeName = "gluten-free")
    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }
    @DynamoDBAttribute(attributeName = "vegan")
    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    @DynamoDBAttribute(attributeName = "calories")
    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealRecord)) return false;
        MealRecord that = (MealRecord) o;
        return protein == that.protein &&
                carb == that.carb &&
                fat == that.fat &&
                calories == that.calories &&
                Objects.equals(mealId, that.mealId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, type, calories, protein, carb, fat);
    }
}
