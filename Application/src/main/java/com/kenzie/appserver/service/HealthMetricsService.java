package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.HealthMetricsRepository;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.service.model.EventType;
import com.kenzie.appserver.service.model.HealthMetrics;
import com.kenzie.appserver.service.model.ScheduledEvent;
import com.kenzie.appserver.service.model.WeightUnit;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.MealData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HealthMetricsService {

    private final HealthMetricsRepository healthMetricsRepository;
    private final MealLambdaServiceClient mealLambdaServiceClient;
    private final ExerciseLambdaServiceClient exerciseLambdaServiceClient;
    private final UserScheduleService userScheduleService;
    private final CacheStore<String, HealthMetricsRecord> cacheStore;
    private ScheduledEventService scheduledEventService;

    @Autowired
    public HealthMetricsService(HealthMetricsRepository healthMetricsRepository,
                                MealLambdaServiceClient mealLambdaServiceClient,
                                ExerciseLambdaServiceClient exerciseLambdaServiceClient,
                                UserScheduleService userScheduleService,
                                CacheStore<String, HealthMetricsRecord> cacheStore) {
        this.healthMetricsRepository = healthMetricsRepository;
        this.mealLambdaServiceClient = mealLambdaServiceClient;
        this.exerciseLambdaServiceClient = exerciseLambdaServiceClient;
        this.userScheduleService = userScheduleService;
        this.cacheStore = cacheStore;
    }

    @Autowired
    public void setScheduledEventService(ScheduledEventService scheduledEventService) {
        this.scheduledEventService = scheduledEventService;
    }

    public HealthMetricsRecord getHealthMetrics(String userId) {
        try {
            HealthMetricsRecord metricsRecord = cacheStore.get(userId);
            if (metricsRecord == null) {
                metricsRecord = retrieveMetricsFromRepository(userId);
            }
            return metricsRecord;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to retrieve health metrics for user: " + userId);
        }
    }

    @Transactional
    public HealthMetricsResponse updateHealthMetrics(HealthMetricsUpdateRequest request) {
        HealthMetricsRecord oldRecord = getHealthMetrics(request.getUserId());
        HealthMetrics healthMetrics = healthMetricsFromRecord(oldRecord);

        if (request.getWeight() != null) {
            healthMetrics.setWeight(request.getWeight());
        }
        if (request.getWeightUnit() != null && !request.getWeightUnit().equals(healthMetrics.getWeightUnit())) {
            healthMetrics.setWeightUnit(request.getWeightUnit());
        }

        double weightInKg = healthMetrics.getWeightUnit().equals(WeightUnit.LBS)
                ? convertPoundsToKilograms(healthMetrics.getWeight())
                : healthMetrics.getWeight();

        UserScheduleResponse currentSchedule = userScheduleService.findCurrentSchedule(request.getUserId());
        for (String eventId : currentSchedule.getScheduledEventIds()) {
            ScheduledEventResponse scheduledEvent = scheduledEventService.findById(eventId);
            if (scheduledEvent.isCompleted() && !scheduledEvent.isMetricsCalculated()) {

                //calculate calories burned via exercise
                if (scheduledEvent.getEventType() == EventType.EXERCISE && scheduledEvent.getExerciseId() != null) {
                    ExerciseData exerciseData = exerciseLambdaServiceClient.findExerciseData(scheduledEvent.getExerciseId());
                    double caloriesBurned = calculateCaloriesBurned(exerciseData.getMETS(), weightInKg, exerciseData.getDuration());
                    healthMetrics.setTotalCalorieExpenditure(healthMetrics.getTotalCalorieExpenditure() + caloriesBurned);
                }

                //add calories and macros from meal intake
                if (scheduledEvent.getEventType() == EventType.MEAL && scheduledEvent.getMealId() != null) {
                    MealData mealData = mealLambdaServiceClient.getMealData(scheduledEvent.getMealId());
                    healthMetrics.setTotalCalorieIntake(healthMetrics.getTotalCalorieIntake() + mealData.getCalories());
                    healthMetrics.setCarbs(healthMetrics.getCarbs() + mealData.getCarb());
                    healthMetrics.setFats(healthMetrics.getFats() + mealData.getFat());
                    healthMetrics.setProtein(healthMetrics.getProtein() + mealData.getProtein());
                }
                scheduledEvent.setMetricsCalculated(true);

                ScheduledEventUpdateRequest updateRequest = new ScheduledEventUpdateRequest();
                updateRequest.setEventId(scheduledEvent.getEventId());
                updateRequest.setMetricsCalculated(scheduledEvent.isMetricsCalculated());

                scheduledEventService.updateScheduledEvent(scheduledEvent.getEventId(), updateRequest);
            }
        }
        HealthMetricsRecord updatedRecord = healthMetricsRepository.save(recordFromHealthMetrics(healthMetrics));

        cacheStore.put(request.getUserId(), updatedRecord);

        return new HealthMetricsResponse(updatedRecord);
    }

    //This method is used only when a specific event is updated
    public void updateMetricsBasedOnEvent(String userId, ScheduledEvent event) {
        HealthMetrics metrics = healthMetricsFromRecord(getHealthMetrics(userId));

        double weightInKg = metrics.getWeightUnit().equals(WeightUnit.LBS)
                ? convertPoundsToKilograms(metrics.getWeight())
                : metrics.getWeight();

        //calculate calories burned via exercise
        if (event.getEventType() == EventType.EXERCISE && event.getExerciseId() != null) {
            ExerciseData exerciseData = exerciseLambdaServiceClient.findExerciseData(event.getExerciseId());
            double caloriesBurned = calculateCaloriesBurned(exerciseData.getMETS(), weightInKg, exerciseData.getDuration());
            metrics.setTotalCalorieExpenditure(metrics.getTotalCalorieExpenditure() + caloriesBurned);
        }

        //add calories and macros from meal intake
        if (event.getEventType() == EventType.MEAL && event.getMealId() != null) {
            MealData mealData = mealLambdaServiceClient.getMealData(event.getMealId());
            metrics.setTotalCalorieIntake(metrics.getTotalCalorieIntake() + mealData.getCalories());
            metrics.setCarbs(metrics.getCarbs() + mealData.getCarb());
            metrics.setFats(metrics.getFats() + mealData.getFat());
            metrics.setProtein(metrics.getProtein() + mealData.getProtein());
        }

        HealthMetricsRecord updatedRecord = healthMetricsRepository.save(recordFromHealthMetrics(metrics));

        cacheStore.put(userId, updatedRecord);
    }

    public void deleteHealthMetrics(String userId) {
        try {
            healthMetricsRepository.deleteById(userId);
            cacheStore.invalidate(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
    }

    public void resetHealthMetrics(String userId) {
        try {
            // Any new HealthMetricsRecord created using JUST a userId will have default values set to 0.0
            HealthMetricsRecord metricsRecord = new HealthMetricsRecord(userId);
            metricsRecord = healthMetricsRepository.save(metricsRecord);
            cacheStore.invalidate(userId);
            cacheStore.put(userId, metricsRecord);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected error occurred");
        }
    }

    private double convertPoundsToKilograms(double pounds) {
        // conversion is 1 kg = 2.20462 lbs
        return pounds / 2.20462;
    }

    private double calculateCaloriesBurned(double MET, double weightKg, int duration) {
        // The MET value represents the energy cost of physical activities.
        // 1 MET is roughly equivalent to the energy cost of sitting quietly.
        // The formula for calories burned per minute is:
        // (MET value * body weight in kg * 3.5) / 200
        // Multiply by the duration of the activity
        return ((MET * weightKg * 3.5) / 200) * duration;
    }

    private HealthMetricsRecord retrieveMetricsFromRepository(String userId) {
        Optional<HealthMetricsRecord> optionalRecord = healthMetricsRepository.findById(userId);
        HealthMetricsRecord metricsRecord = optionalRecord.orElseGet(() -> new HealthMetricsRecord(userId));
        cacheStore.put(userId, metricsRecord);
        return metricsRecord;
    }

    private HealthMetrics healthMetricsFromRecord(HealthMetricsRecord record) {
        HealthMetrics metrics = new HealthMetrics();
        metrics.setUserId(record.getUserId());
        metrics.setWeight(record.getWeight());
        metrics.setWeightUnit(record.getWeightUnit());
        metrics.setTotalCalorieIntake(record.getTotalCalorieIntake());
        metrics.setTotalCalorieExpenditure(record.getTotalCalorieExpenditure());
        metrics.setCarbs(record.getCarbs());
        metrics.setFats(record.getFats());
        metrics.setProtein(record.getProtein());
        return metrics;
    }

    private HealthMetricsRecord recordFromHealthMetrics(HealthMetrics metrics) {
        HealthMetricsRecord record = new HealthMetricsRecord();
        record.setUserId(metrics.getUserId());
        record.setWeight(metrics.getWeight());
        record.setWeightUnit(metrics.getWeightUnit());
        record.setTotalCalorieIntake(metrics.getTotalCalorieIntake());
        record.setTotalCalorieExpenditure(metrics.getTotalCalorieExpenditure());
        record.setCarbs(metrics.getCarbs());
        record.setFats(metrics.getFats());
        record.setProtein(metrics.getProtein());
        return record;
    }
}
