package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.MealDao;
import com.kenzie.capstone.service.model.MealData;
import com.kenzie.capstone.service.model.MealRecord;

import javax.inject.Inject;
import java.util.List;

public class MealService {
    private final MealDao dao;

    @Inject
    public MealService(MealDao dao){
        this.dao = dao;
    }

    public MealData getMealData(String mealId){
        List<MealRecord> records = dao.findMealData(mealId);
        if(records.size() > 0){
            return new MealData(records.get(0).getMealId(), records.get(0).getType(), records.get(0).getRecipe(),
                    records.get(0).getName(), records.get(0).getDescription(), records.get(0).getFat(), records.get(0).getProtein(),
                    records.get(0).getCarb(),records.get(0).isGlutenFree(), records.get(0).isVegan());
        }
        return null;
    }

    public MealData setData(String mealId, String type,
                            String recipe, String name, String description, int fat,
                            int protein, int carb, boolean glutenFree, boolean vegan) {
        MealRecord record = dao.setMealRecord(mealId, type, recipe, name, description,
                fat, protein, carb, glutenFree, vegan);

        return new MealData(mealId, type, recipe, name, description,
                fat, protein, carb, glutenFree, vegan);
    }
}
