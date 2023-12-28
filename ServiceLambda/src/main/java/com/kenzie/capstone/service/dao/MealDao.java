package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.MealData;
import com.kenzie.capstone.service.model.MealRecord;

import java.util.List;

public class MealDao {
    private final DynamoDBMapper mapper;

    public MealDao(DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    public MealData addMealData(MealData mealData){
        try{
            mapper.save(mealData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of("mealId", new ExpectedAttributeValue().withExists(false)
                    )));
        }catch(ConditionalCheckFailedException e){
            throw new IllegalArgumentException("id has already been used");
        }
        return mealData;
    }
    public List<MealRecord> findMealData(String mealId){
        MealRecord record = new MealRecord();
        record.setMealId(mealId);

        DynamoDBQueryExpression<MealRecord> query = new DynamoDBQueryExpression<MealRecord>()
                .withHashKeyValues(record)
                .withConsistentRead(false);

        return mapper.query(MealRecord.class, query);
    }
    public MealRecord setMealRecord(String mealId,
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

        MealRecord record = new MealRecord();
        record.setMealId(mealId);
        record.setName(name);
        record.setDescription(description);
        record.setRecipe(recipe);
        record.setType(type);
        record.setCalories(calories);
        record.setProtein(protein);
        record.setCarb(carb);
        record.setFat(fat);
        record.setGlutenFree(glutenFree);
        record.setVegan(vegan);

        try {
            mapper.save(record, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "mealId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("mealId already exists");
        }
        return record;
    }

}
