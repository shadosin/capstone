package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.MealDao;
import com.kenzie.capstone.service.model.MealData;
import com.kenzie.capstone.service.model.MealRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                    records.get(0).getUrl(),
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
                            String url,
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
                url,
                type,
                calories,
                protein,
                carb,
                fat,
                glutenFree,
                vegan);

        return new MealData(mealId,
                name,
                url,
                type,
                calories,
                protein,
                carb,
                fat,
                glutenFree,
                vegan);
    }

    public List<MealData> getMealDataFromAttributeValue(String attributeValue){
        List<MealRecord> records = dao.getMealFromGSIByAttributeValue(attributeValue);
        if(!records.isEmpty()){
            return records.stream()
                    .map(record -> new MealData(
                            record.getMealId(),
                            record.getName(),
                            record.getUrl(),
                            record.getType(),
                            record.getCalories(),
                            record.getProtein(),
                            record.getCarb(),
                            record.getFat(),
                            record.isGlutenFree(),
                            record.isVegan()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
