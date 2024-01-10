package com.kenzie.capstone.service.model;

import java.util.Objects;

public class MealData {
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

    public MealData() {}

    public MealData(String mealId,
                    String name,
                    String url,
                    String type,
                    double calories,
                    double protein,
                    double carb,
                    double fat,
                    boolean glutenFree,
                    boolean vegan){
        this.mealId = mealId;
        this.name = name;
        this.url = url;
        this.type = type;
        this.calories = calories;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat ;
        this.glutenFree = glutenFree;
        this.vegan = vegan;
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
        if (!(o instanceof MealData)) return false;
        MealData mealData = (MealData) o;
        return protein == mealData.protein &&
                carb == mealData.carb &&
                fat == mealData.fat &&
                calories == mealData.calories &&
                Objects.equals(mealId, mealData.mealId) &&
                Objects.equals(type, mealData.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, type,calories, protein, carb, fat);
    }
}
