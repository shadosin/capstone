package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.ExerciseEventDataResponse;
import com.kenzie.appserver.controller.model.MealEventDataResponse;
import com.kenzie.capstone.service.client.ApiGatewayException;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.MealData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EventDataServiceTest {

    @Mock
    private ExerciseLambdaServiceClient exerciseLambdaServiceClient;

    @Mock
    private MealLambdaServiceClient mealLambdaServiceClient;

    @InjectMocks
    private EventDataService eventDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMealDataById_Success() throws Exception {
        String mealId = "meal123";
        MealData expectedMealData = new MealData();
        expectedMealData.setMealId(mealId);
        when(mealLambdaServiceClient.getMealData(mealId)).thenReturn(expectedMealData);

        MealEventDataResponse response = eventDataService.getMealDataById(mealId);

        assertNotNull(response);
        assertEquals(expectedMealData.getMealId(), response.getMealId());
    }

    @Test
    void testGetMealDataById_Exception() {
        String mealId = "invalidMealId";
        when(mealLambdaServiceClient.getMealData(mealId)).thenThrow(new ApiGatewayException("Error"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventDataService.getMealDataById(mealId));

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetExerciseDataById_Success() throws Exception {
        String exerciseId = "exercise123";
        ExerciseData expectedExerciseData = new ExerciseData();
        expectedExerciseData.setExerciseId(exerciseId);
        when(exerciseLambdaServiceClient.findExerciseData(exerciseId)).thenReturn(expectedExerciseData);

        ExerciseEventDataResponse response = eventDataService.getExerciseDataById(exerciseId);

        assertNotNull(response);
        assertEquals(expectedExerciseData.getExerciseId(), response.getExerciseId());
    }

    @Test
    void testGetExerciseDataById_Exception() {
        String exerciseId = "invalidExerciseId";
        when(exerciseLambdaServiceClient.findExerciseData(exerciseId)).thenThrow(new ApiGatewayException("Error"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventDataService.getExerciseDataById(exerciseId));

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetMealDataByType_Success() throws Exception {
        String type = "Vegetarian";
        List<MealData> mealDataList = Arrays.asList(new MealData(), new MealData());
        when(mealLambdaServiceClient.getMealDataFromGSI(type)).thenReturn(mealDataList);

        List<MealEventDataResponse> responseList = eventDataService.getMealDataByType(type);

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
    }

    @Test
    void testGetMealDataByType_Exception() {
        String type = "UnknownType";
        when(mealLambdaServiceClient.getMealDataFromGSI(type)).thenThrow(new ApiGatewayException("Error"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventDataService.getMealDataByType(type));

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetExerciseDataByType_Success() throws Exception {
        String type = "Cardio";
        List<ExerciseData> exerciseDataList = Arrays.asList(new ExerciseData(), new ExerciseData());
        when(exerciseLambdaServiceClient.getExerciseDataFromGSI(type)).thenReturn(exerciseDataList);

        List<ExerciseEventDataResponse> responseList = eventDataService.getExerciseDataByType(type);

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
    }

    @Test
    void testGetExerciseDataByType_Exception() {
        String type = "UnknownType";
        when(exerciseLambdaServiceClient.getExerciseDataFromGSI(type)).thenThrow(new ApiGatewayException("Error"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventDataService.getExerciseDataByType(type));

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

