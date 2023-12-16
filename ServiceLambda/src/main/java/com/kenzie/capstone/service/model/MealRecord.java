package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
@DynamoDBTable(tableName = "Meals")
public class MealRecord {

    private String mealId;

    private String name;

    private String description;

    private String recipe;

    private String type;

    private int protein;

    private int carb;

    private int fat;

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
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @DynamoDBAttribute(attributeName = "recipe")
    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    @DynamoDBRangeKey(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @DynamoDBAttribute(attributeName = "protein")
    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
    @DynamoDBAttribute(attributeName = "carb")
    public int getCarb() {
        return carb;
    }

    public void setCarb(int carb) {
        this.carb = carb;
    }
    @DynamoDBAttribute(attributeName = "fat")
    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
    @DynamoDBAttribute(attributeName = "glutenFree")
    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }
    @DynamoDBAttribute(attributeName = "glutenFree")
    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealRecord)) return false;
        MealRecord that = (MealRecord) o;
        return protein == that.protein &&
                carb == that.carb &&
                fat == that.fat &&
                Objects.equals(mealId, that.mealId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, type, protein, carb, fat);
    }
}
