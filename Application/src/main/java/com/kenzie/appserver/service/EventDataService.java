package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.ExerciseEventDataResponse;
import com.kenzie.appserver.controller.model.MealEventDataResponse;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.MealData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventDataService {

    private final ExerciseLambdaServiceClient exerciseLambdaServiceClient;
    private final MealLambdaServiceClient mealLambdaServiceClient;

    public EventDataService(ExerciseLambdaServiceClient exerciseLambdaServiceClient,
                            MealLambdaServiceClient mealLambdaServiceClient) {
        this.exerciseLambdaServiceClient = exerciseLambdaServiceClient;
        this.mealLambdaServiceClient = mealLambdaServiceClient;
    }

    public MealEventDataResponse getMealDataById(String mealId) {
        try {
            return new MealEventDataResponse(mealLambdaServiceClient.getMealData(mealId));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<MealEventDataResponse> getMealDataByType(String type) {
        try {
            List<MealData> mealDataList = mealLambdaServiceClient.getMealDataFromGSI(type);
            return mealDataList.stream()
                    .map(MealEventDataResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ExerciseEventDataResponse getExerciseDataById(String exerciseId) {
        try {
            return new ExerciseEventDataResponse(exerciseLambdaServiceClient.findExerciseData(exerciseId));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<ExerciseEventDataResponse> getExerciseDataByType(String type) {
        try {
            List<ExerciseData> exerciseDataList = exerciseLambdaServiceClient.getExerciseDataFromGSI(type);
            return exerciseDataList.stream()
                    .map(ExerciseEventDataResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
