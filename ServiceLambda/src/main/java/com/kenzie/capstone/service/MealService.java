package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.MealDao;
import com.kenzie.capstone.service.model.MealData;
import com.kenzie.capstone.service.model.MealRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class MealService {
    private final MealDao dao;

    @Inject
    public MealService(MealDao dao){
        this.dao = dao;
    }

    public MealData getMealData(String mealId){
        List<MealRecord> records = dao.findMealData(mealId);
        if(!records.isEmpty()){
            return new MealData(records.get(0).getMealId(),
                    records.get(0).getName(),
                    records.get(0).getDescription(),
                    records.get(0).getRecipe(),
                    records.get(0).getType(),
                    records.get(0).getCalories(),
                    records.get(0).getProtein(),
                    records.get(0).getCarb(),
                    records.get(0).getFat(),
                    records.get(0).isGlutenFree(),
                    records.get(0).isVegan());
        }
        return null;
    }

    public MealData setData(String name,
                            String description,
                            String recipe,
                            String type,
                            double calories,
                            double protein,
                            double carb,
                            double fat,
                            boolean glutenFree,
                            boolean vegan) {

        String mealId = UUID.randomUUID().toString();

        MealRecord record = dao.setMealRecord(mealId,
                name,
                description,
                recipe,
                type,
                calories,
                protein,
                carb,
                fat,
                glutenFree,
                vegan);

        return new MealData(mealId,
                name,
                description,
                recipe,
                type,
                calories,
                protein,
                carb,
                fat,
                glutenFree,
                vegan);
    }
}
