package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.HealthMetricsRepository;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.service.model.EventType;
import com.kenzie.appserver.service.model.WeightUnit;
import com.kenzie.appserver.utils.ScheduledEventConverter;
import com.kenzie.appserver.utils.UserScheduleConverter;
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
    private final ScheduledEventService scheduledEventService;
    private final CacheStore<String, HealthMetricsRecord> cacheStore;

    @Autowired
    public HealthMetricsService(HealthMetricsRepository healthMetricsRepository,
                                MealLambdaServiceClient mealLambdaServiceClient,
                                ExerciseLambdaServiceClient exerciseLambdaServiceClient,
                                UserScheduleService userScheduleService,
                                ScheduledEventService scheduledEventService,
                                CacheStore<String, HealthMetricsRecord> cacheStore) {
        this.healthMetricsRepository = healthMetricsRepository;
        this.mealLambdaServiceClient = mealLambdaServiceClient;
        this.exerciseLambdaServiceClient = exerciseLambdaServiceClient;
        this.userScheduleService = userScheduleService;
        this.scheduledEventService = scheduledEventService;
        this.cacheStore = cacheStore;
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
        HealthMetricsRecord metricsRecord = getHealthMetrics(request.getUserId());

        double weightInKg = request.getWeightUnit().equals(WeightUnit.LBS)
                ? convertPoundsToKilograms(request.getWeight())
                : request.getWeight();

        UserScheduleRecord currentSchedule = UserScheduleConverter.convertToRecordFromResponse(userScheduleService.findCurrentSchedule(request.getUserId()));
        for (String eventId : currentSchedule.getScheduledEventIds()) {
            ScheduledEventRecord scheduledEvent = ScheduledEventConverter.createRecordFromResponse(scheduledEventService.findById(eventId));
            if (scheduledEvent.isCompleted() && !scheduledEvent.isMetricsCalculated()) {

                //calculate calories burned via exercise
                if (scheduledEvent.getEventType() == EventType.EXERCISE) {
                    ExerciseData exerciseData = exerciseLambdaServiceClient.findExerciseData(scheduledEvent.getExerciseId());
                    double caloriesBurned = calculateCaloriesBurned(exerciseData.getMETS(), weightInKg, exerciseData.getDuration());
                    metricsRecord.setTotalCalorieExpenditure(metricsRecord.getTotalCalorieExpenditure() + caloriesBurned);
                }

                //add calories and macros from meal intake
                if (scheduledEvent.getEventType() == EventType.MEAL) {
                    MealData mealData = mealLambdaServiceClient.getMealData(scheduledEvent.getEventId());
                    metricsRecord.setTotalCalorieIntake(metricsRecord.getTotalCalorieIntake() + mealData.getCalories());
                    metricsRecord.setCarbs(metricsRecord.getCarbs() + mealData.getCarb());
                    metricsRecord.setFats(metricsRecord.getFats() + mealData.getFat());
                    metricsRecord.setProtein(metricsRecord.getProtein() + mealData.getProtein());
                }
                scheduledEvent.setMetricsCalculated(true);

                ScheduledEventUpdateRequest updateRequest = new ScheduledEventUpdateRequest();
                updateRequest.setEventId(scheduledEvent.getEventId());
                updateRequest.setMetricsCalculated(scheduledEvent.isMetricsCalculated());

                scheduledEventService.updateScheduledEvent(scheduledEvent.getEventId(), updateRequest);
            }
        }
        HealthMetricsRecord updatedRecord = healthMetricsRepository.save(metricsRecord);

        cacheStore.put(request.getUserId(), updatedRecord);

        return new HealthMetricsResponse(updatedRecord);
    }

    public void updateMetricsBasedOnEvent(String userId, ScheduledEventRecord event) {
        HealthMetricsRecord metricsRecord = getHealthMetrics(userId);
        double weightInKg = convertWeightToKg(metricsRecord.getWeight(), metricsRecord.getWeightUnit());

        //calculate calories burned via exercise
        if (event.getEventType() == EventType.EXERCISE && event.getExerciseId() != null) {
            ExerciseData exerciseData = exerciseLambdaServiceClient.findExerciseData(event.getExerciseId());
            double caloriesBurned = calculateCaloriesBurned(exerciseData.getMETS(), weightInKg, exerciseData.getDuration());
            metricsRecord.setTotalCalorieExpenditure(metricsRecord.getTotalCalorieExpenditure() + caloriesBurned);
        }

        //add calories and macros from meal intake
        if (event.getEventType() == EventType.MEAL && event.getMealId() != null) {
            MealData mealData = mealLambdaServiceClient.getMealData(event.getMealId());
            metricsRecord.setTotalCalorieIntake(metricsRecord.getTotalCalorieIntake() + mealData.getCalories());
            metricsRecord.setCarbs(metricsRecord.getCarbs() + mealData.getCarb());
            metricsRecord.setFats(metricsRecord.getFats() + mealData.getFat());
            metricsRecord.setProtein(metricsRecord.getProtein() + mealData.getProtein());
        }

        HealthMetricsRecord updatedRecord = healthMetricsRepository.save(metricsRecord);

        cacheStore.put(userId, updatedRecord);
    }

    public void deleteHealthMetrics(String userId) {
        try {
            healthMetricsRepository.deleteById(userId);
            cacheStore.invalidate(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user ID");
        }
    }

    public void resetHealthMetrics(String userId) {
        try {
            // Any new HealthMetricsRecord created using JUST a userId will have default values set to 0.0
            HealthMetricsRecord metricsRecord = new HealthMetricsRecord(userId);
            metricsRecord = healthMetricsRepository.save(metricsRecord);
            cacheStore.put(userId, metricsRecord);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected error occurred");
        }
    }

    private double convertPoundsToKilograms(double pounds) {
        // conversion is 1 kg = 2.20462 lbs
        return pounds / 2.20462;
    }

    private double convertWeightToKg(double weight, WeightUnit weightUnit) {
        return weightUnit.equals(WeightUnit.LBS) ? convertPoundsToKilograms(weight) : weight;
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
        HealthMetricsRecord metricsRecord = optionalRecord.orElseGet(() -> {
            HealthMetricsRecord newRecord = new HealthMetricsRecord(userId);

            return newRecord;
        });
        cacheStore.put(userId, metricsRecord);
        return metricsRecord;
    }

    public void invalidateCache(String userId) {
        cacheStore.invalidate(userId);
    }
}
