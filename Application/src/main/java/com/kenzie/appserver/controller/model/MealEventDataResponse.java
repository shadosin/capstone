package com.kenzie.appserver.controller.model;

import com.kenzie.capstone.service.model.MealData;

import java.util.Objects;

public class MealEventDataResponse {

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

    public MealEventDataResponse() {}

    public MealEventDataResponse(MealData mealData) {
        this.mealId = mealData.getMealId();
        this.name = mealData.getName();
        this.url = mealData.getUrl();
        this.type = mealData.getType();
        this.calories = mealData.getCalories();
        this.protein = mealData.getProtein();
        this.carb = mealData.getCarb();
        this.fat = mealData.getFat();
        this.glutenFree = mealData.isGlutenFree();
        this.vegan = mealData.isVegan();
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealEventDataResponse that = (MealEventDataResponse) o;
        return Double.compare(calories, that.calories) == 0 &&
                Double.compare(protein, that.protein) == 0 &&
                Double.compare(carb, that.carb) == 0 &&
                Double.compare(fat, that.fat) == 0 &&
                glutenFree == that.glutenFree &&
                vegan == that.vegan &&
                Objects.equals(mealId, that.mealId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, name, url, type, calories, protein, carb, fat, glutenFree, vegan);
    }
}
