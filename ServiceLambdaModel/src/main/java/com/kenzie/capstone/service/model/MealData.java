package com.kenzie.capstone.service.model;

import java.util.Objects;

public class MealData {

    private String mealId;

    private String name;

    private String description;

    private String recipe;

    private String type;

    private double calories;

    private double protein;

    private double carb;

    private double fat;

    private boolean glutenFree;

    private boolean vegan;

    public MealData(String mealId,
                    String name,
                    String description,
                    String recipe,
                    String type,
                    double calories,
                    double protein,
                    double carb,
                    double fat,
                    boolean glutenFree,
                    boolean vegan){
        this.mealId = mealId;
        this.name = name;
        this.description = description;
        this.recipe = recipe;
        this.type = type;
        this.calories = calories;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat ;
        this.glutenFree = glutenFree;
        this.vegan = vegan;
    }

    public String getType() {
        return type;
    }

    public String getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRecipe() {
        return recipe;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarb() {
        return carb;
    }

    public double getFat() {
        return fat;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isVegan() {
        return vegan;
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
