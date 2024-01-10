package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.events.ScheduledEventUpdateEvent;
import com.kenzie.appserver.repositories.HealthMetricsRepository;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.MealData;
import org.junit.jupiter.api.BeforeEach;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.service.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class HealthMetricsServiceTest {
    private HealthMetricsRepository healthMetricsRepository;
    private MealLambdaServiceClient mealLambdaServiceClient;
    private ExerciseLambdaServiceClient exerciseLambdaServiceClient;
    private CacheStore<String, HealthMetricsRecord> cacheStore;
    private HealthMetricsService healthMetricsService;

    @BeforeEach
    public void setup() {
        healthMetricsRepository = mock(HealthMetricsRepository.class);
        mealLambdaServiceClient = mock(MealLambdaServiceClient.class);
        exerciseLambdaServiceClient = mock(ExerciseLambdaServiceClient.class);
        cacheStore = mock(CacheStore.class);

        healthMetricsService = new HealthMetricsService(healthMetricsRepository, mealLambdaServiceClient,
                exerciseLambdaServiceClient, cacheStore);
    }

    @Test
    public void testHandleScheduledEventUpdate_WhenEventIsCompletedAndMetricsNotCalculated() {
        String userId = "testUserId";
        ScheduledEvent mockEvent = mock(ScheduledEvent.class);
        when(mockEvent.isCompleted()).thenReturn(true);
        when(mockEvent.isMetricsCalculated()).thenReturn(false);

        ScheduledEventUpdateEvent eventUpdate = new ScheduledEventUpdateEvent(this, userId, mockEvent);

        HealthMetricsRecord metricsRecord = new HealthMetricsRecord(userId);

        when(cacheStore.get(userId)).thenReturn(metricsRecord);
        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(metricsRecord));

        healthMetricsService.handleScheduledEventUpdate(eventUpdate);

        verify(healthMetricsRepository, times(1)).save(any(HealthMetricsRecord.class));
    }

    @Test
    public void testHandleScheduledEventUpdate_NoUpdateWhenEventNotCompletedOrMetricsCalculated() {
        String userId = "testUserId";
        ScheduledEvent mockEvent = mock(ScheduledEvent.class);

        when(mockEvent.isCompleted()).thenReturn(false);
        when(mockEvent.isMetricsCalculated()).thenReturn(false);

        ScheduledEventUpdateEvent eventUpdate = new ScheduledEventUpdateEvent(this, userId, mockEvent);

        HealthMetricsRecord metricsRecord = new HealthMetricsRecord();

        when(cacheStore.get(userId)).thenReturn(metricsRecord);
        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(metricsRecord));

        healthMetricsService.handleScheduledEventUpdate(eventUpdate);

        verify(healthMetricsRepository, never()).save(any(HealthMetricsRecord.class));
    }

    @Test
    public void testGetHealthMetrics() {

        String userId = "testUserId";
        HealthMetricsRecord mockMetricsRecord = new HealthMetricsRecord(userId);

        when(cacheStore.get(userId)).thenReturn(null);

        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(mockMetricsRecord));

        HealthMetricsRecord result = healthMetricsService.getHealthMetrics(userId);

        assertNotNull(result);
        assertSame(mockMetricsRecord, result);
        Mockito.verify(cacheStore, times(1)).put(userId, mockMetricsRecord);
    }

    @Test
    public void testDeleteHealthMetrics() {
        String userId = "testUserId";

        healthMetricsService.deleteHealthMetrics(userId);

        Mockito.verify(healthMetricsRepository).deleteById(userId);
        Mockito.verify(cacheStore).invalidate(userId);
    }

    @Test
    public void testResetHealthMetrics() {
        String userId = "testUserId";
        HealthMetricsRecord mockMetricsRecord = new HealthMetricsRecord(userId);

        when(healthMetricsRepository.save(any(HealthMetricsRecord.class))).thenReturn(mockMetricsRecord);

        healthMetricsService.resetHealthMetrics(userId);

        Mockito.verify(healthMetricsRepository).save(any(HealthMetricsRecord.class));
        Mockito.verify(cacheStore).put(eq(userId), any(HealthMetricsRecord.class));
    }

    @Test
    public void updateHealthMetrics_UpdatesMetricsSuccessfully() {
        String userId = "testUserId";
        HealthMetricsUpdateRequest updateRequest = new HealthMetricsUpdateRequest();
        updateRequest.setUserId(userId);
        updateRequest.setWeight(75.0);
        updateRequest.setWeightUnit(WeightUnit.KG);

        HealthMetricsRecord existingRecord = new HealthMetricsRecord();
        existingRecord.setUserId(userId);
        existingRecord.setWeight(70.0);
        existingRecord.setWeightUnit(WeightUnit.LBS);
        existingRecord.setTotalCalorieIntake(2000.0);
        existingRecord.setTotalCalorieExpenditure(1800.0);
        existingRecord.setCarbs(250.0);
        existingRecord.setFats(50.0);
        existingRecord.setProtein(75.0);

        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(existingRecord));

        HealthMetricsRecord updatedRecord = new HealthMetricsRecord();
        updatedRecord.setUserId(userId);
        updatedRecord.setWeight(75.0);
        updatedRecord.setWeightUnit(WeightUnit.KG);
        updatedRecord.setTotalCalorieIntake(2000.0);
        updatedRecord.setTotalCalorieExpenditure(1800.0);
        updatedRecord.setCarbs(250.0);
        updatedRecord.setFats(50.0);
        updatedRecord.setProtein(75.0);

        when(healthMetricsRepository.save(any(HealthMetricsRecord.class))).thenReturn(updatedRecord);

        HealthMetricsResponse response = healthMetricsService.updateHealthMetrics(updateRequest);

        // Assert and Verify
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(75.0, response.getWeight());
        assertEquals(WeightUnit.KG, response.getWeightUnit());

        verify(healthMetricsRepository).findById(userId);
        verify(healthMetricsRepository).save(any(HealthMetricsRecord.class));
    }

    @Test
    public void updateMetricsBasedOnEvent_userWeightSetToKg_UpdatesMetricsForExerciseEvent() {
        // Given
        String userId = "testUserId";
        double initialWeight = 70.0; // weightInKg

        String exerciseId = "testExerciseId";
        int exerciseDuration = 30;
        double MET = 8.0;
        // logic the calculateCaloriesBurned private method performs
        double expectedCaloriesBurned = ((MET * initialWeight * 3.5) / 200) * exerciseDuration;

        ScheduledEvent exerciseEvent = new ScheduledEvent();
        exerciseEvent.setEventType(EventType.EXERCISE);
        exerciseEvent.setExerciseId(exerciseId);
        exerciseEvent.setCompleted(true);

        ExerciseData exerciseData = new ExerciseData();
        exerciseData.setExerciseId(exerciseId);
        exerciseData.setMETS(MET);
        exerciseData.setDuration(exerciseDuration);

        HealthMetricsRecord existingRecord = new HealthMetricsRecord();
        existingRecord.setUserId(userId);
        existingRecord.setWeight(initialWeight);
        existingRecord.setWeightUnit(WeightUnit.KG);
        existingRecord.setTotalCalorieIntake(1000.0);
        existingRecord.setTotalCalorieExpenditure(0.0);
        existingRecord.setCarbs(50.0);
        existingRecord.setFats(70.0);
        existingRecord.setProtein(90.0);

        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(existingRecord));
        when(exerciseLambdaServiceClient.findExerciseData(exerciseId)).thenReturn(exerciseData);

        healthMetricsService.updateMetricsBasedOnEvent(userId, exerciseEvent);

        ArgumentCaptor<HealthMetricsRecord> recordCaptor = ArgumentCaptor.forClass(HealthMetricsRecord.class);
        verify(healthMetricsRepository).save(recordCaptor.capture());
        HealthMetricsRecord capturedRecord = recordCaptor.getValue();
        assertEquals(expectedCaloriesBurned, capturedRecord.getTotalCalorieExpenditure());
    }

    @Test
    public void updateMetricsBasedOnEvent_userWeightSetToLbs_UpdatesMetricsForExerciseEvent() {
        // Given
        String userId = "testUserId";
        double initialWeight = 100.0; // weightInLbs

        String exerciseId = "testExerciseId";
        int exerciseDuration = 30;
        double MET = 8.0;

        // logic that convertPoundsToKilograms private method performs
        double initialWeightInKg = initialWeight / 2.20462;

        // logic the calculateCaloriesBurned private method performs
        double expectedCaloriesBurned = ((MET * initialWeightInKg * 3.5) / 200) * exerciseDuration;

        ScheduledEvent exerciseEvent = new ScheduledEvent();
        exerciseEvent.setEventType(EventType.EXERCISE);
        exerciseEvent.setExerciseId(exerciseId);
        exerciseEvent.setCompleted(true);

        ExerciseData exerciseData = new ExerciseData();
        exerciseData.setExerciseId(exerciseId);
        exerciseData.setMETS(MET);
        exerciseData.setDuration(exerciseDuration);

        HealthMetricsRecord existingRecord = new HealthMetricsRecord();
        existingRecord.setUserId(userId);
        existingRecord.setWeight(initialWeight);
        existingRecord.setWeightUnit(WeightUnit.LBS);
        existingRecord.setTotalCalorieIntake(1000.0);
        existingRecord.setTotalCalorieExpenditure(0.0);
        existingRecord.setCarbs(50.0);
        existingRecord.setFats(70.0);
        existingRecord.setProtein(90.0);

        when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(existingRecord));
        when(exerciseLambdaServiceClient.findExerciseData(exerciseId)).thenReturn(exerciseData);

        healthMetricsService.updateMetricsBasedOnEvent(userId, exerciseEvent);

        ArgumentCaptor<HealthMetricsRecord> recordCaptor = ArgumentCaptor.forClass(HealthMetricsRecord.class);
        verify(healthMetricsRepository).save(recordCaptor.capture());
        HealthMetricsRecord capturedRecord = recordCaptor.getValue();
        assertEquals(expectedCaloriesBurned, capturedRecord.getTotalCalorieExpenditure());
    }

    @Test
    public void updateMetricsBasedOnEvent_UpdatesMetricsForMealEvent() {
        String userId = "testUserId";
        double initialWeight = 70.0; // weightInKg
        String eventId = "testEventId";
        String mealId = "testMealId";

        ScheduledEvent mealEvent = new ScheduledEvent();
        mealEvent.setEventType(EventType.MEAL);
        mealEvent.setEventId(eventId);
        mealEvent.setMealId(mealId);
        mealEvent.setCompleted(true);

        MealData mealData = new MealData();
        mealData.setCarb(50.0);
        mealData.setMealId(mealId);
        mealData.setFat(70.0);
        mealData.setCalories(100.0);
        mealData.setVegan(false);
        mealData.setName("food");
        mealData.setUrl("www.url.com");
        mealData.setGlutenFree(false);
        mealData.setProtein(90.0);
        mealData.setType("stuff");


        HealthMetricsRecord existingRecord = new HealthMetricsRecord();
        existingRecord.setUserId(userId);
        existingRecord.setWeight(initialWeight);
        existingRecord.setWeightUnit(WeightUnit.KG);
        existingRecord.setTotalCalorieIntake(1000.0);
        existingRecord.setTotalCalorieExpenditure(100.0);
        existingRecord.setCarbs(50.0);
        existingRecord.setFats(70.0);
        existingRecord.setProtein(90.0);

        double updatedCalorieIntake = existingRecord.getTotalCalorieIntake() + mealData.getCalories();
        double updatedCarbs = existingRecord.getCarbs() + mealData.getCarb();
        double updatedFats = existingRecord.getFats() + mealData.getFat();
        double updatedProtein = existingRecord.getProtein() + mealData.getProtein();

        when(cacheStore.get(userId)).thenReturn(existingRecord);
        //when(healthMetricsRepository.findById(userId)).thenReturn(Optional.of(existingRecord));
        when(mealLambdaServiceClient.getMealData(mealId)).thenReturn(mealData);

        // Initialize ArgumentCaptors
        ArgumentCaptor<HealthMetricsRecord> recordCaptor = ArgumentCaptor.forClass(HealthMetricsRecord.class);
        ArgumentCaptor<HealthMetricsRecord> cacheCaptor = ArgumentCaptor.forClass(HealthMetricsRecord.class);

        // Call the method under test
        healthMetricsService.updateMetricsBasedOnEvent(userId, mealEvent);

        verify(mealLambdaServiceClient).getMealData(eq(mealId));

        // Capture the argument passed to healthMetricsRepository.save
        verify(healthMetricsRepository).save(recordCaptor.capture());
        HealthMetricsRecord savedRecord = recordCaptor.getValue();

        // Capture the argument passed to cacheStore.put
        verify(cacheStore).put(eq(userId), cacheCaptor.capture());

        // Perform assertions on the saved record
        assertEquals(updatedCalorieIntake, savedRecord.getTotalCalorieIntake(), "Calorie intake should be updated correctly");
        assertEquals(updatedCarbs, savedRecord.getCarbs(), "Carbs should be updated correctly");
        assertEquals(updatedFats, savedRecord.getFats(), "Fats should be updated correctly");
        assertEquals(updatedProtein, savedRecord.getProtein(), "Protein should be updated correctly");
    }

    @Test
    public void getHealthMetrics_ReturnsCachedValue() {
        String userId = "testUserId";
        HealthMetricsRecord cachedRecord = new HealthMetricsRecord(userId);
        cachedRecord.setWeight(70.0);

        when(cacheStore.get(userId)).thenReturn(cachedRecord);

        HealthMetricsRecord result = healthMetricsService.getHealthMetrics(userId);

        assertNotNull(result);
        assertSame(cachedRecord, result);
        verify(cacheStore, times(0)).put(eq(userId), any(HealthMetricsRecord.class));
    }

    @Test
    public void getHealthMetrics_ThrowsException() {
        String userId = "testUserId";

        when(cacheStore.get(userId)).thenThrow(new RuntimeException("Simulating cache retrieval failure"));

        assertThrows(IllegalArgumentException.class, () -> healthMetricsService.getHealthMetrics(userId));

        verify(cacheStore, times(0)).put(eq(userId), any(HealthMetricsRecord.class));
    }
    @Test
    public void deleteHealthMetrics_ThrowsException(){
        String invalidUserId = "invalidUserId";

        doThrow(new IllegalArgumentException("Invalid user ID: " + invalidUserId))
                .when(healthMetricsRepository).deleteById(invalidUserId);

        assertThrows(IllegalArgumentException.class, () -> healthMetricsService.deleteHealthMetrics(invalidUserId));

        verify(healthMetricsRepository).deleteById(invalidUserId);
        verify(cacheStore, times(0)).invalidate(invalidUserId);
    }
    @Test
    public void resetHealthMetrics_ThrowsExceptionForUnexpectedError() {
        String userId = "testUserId";

        doThrow(new IllegalArgumentException("Unexpected error occurred"))
                .when(healthMetricsRepository).save(any(HealthMetricsRecord.class));

        assertThrows(IllegalArgumentException.class, () -> healthMetricsService.resetHealthMetrics(userId));

        verify(healthMetricsRepository).save(any(HealthMetricsRecord.class));
        verify(cacheStore, times(0)).invalidate(userId);
        verify(cacheStore, times(0)).put(eq(userId), any(HealthMetricsRecord.class));
    }
}
