package com.kenzie.capstone.service.model;

import java.util.Objects;

public class MealData {

    private String mealId;

    private String name;

    private String description;

    private String recipe;

    private int protein;

    private int carb;

    private int fat;

    private boolean glutenFree;

    private boolean vegan;

    public MealData(String mealId,
                    String name,
                    String description,
                    String recipe,
                    int protein,
                    int carb,
                    int fat,
                    boolean glutenFree,
                    boolean vegan){
        this.mealId = mealId;
        this.name = name;
        this.description = description;
        this.recipe = recipe;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat ;
        this.glutenFree = glutenFree;
        this.vegan = vegan;
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

    public int getProtein() {
        return protein;
    }

    public int getCarb() {
        return carb;
    }

    public int getFat() {
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
                Objects.equals(mealId, mealData.mealId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, protein, carb, fat);
    }
}
