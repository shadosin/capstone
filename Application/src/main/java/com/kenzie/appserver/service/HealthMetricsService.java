package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.controller.model.CreateHealthMetricsRequest;
import com.kenzie.appserver.controller.model.HealthMetricsResponse;
import com.kenzie.appserver.controller.model.HealthMetricsUpdateRequest;
import com.kenzie.appserver.controller.model.UserScheduleResponse;
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

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
        HealthMetricsRecord metricsRecord = cacheStore.get(userId);
        if (metricsRecord == null) {
            Optional<HealthMetricsRecord> optionalRecord = healthMetricsRepository.findById(userId);
            if (optionalRecord.isPresent()) {
                metricsRecord = optionalRecord.get();
                cacheStore.put(userId, metricsRecord);
            } else {
                metricsRecord = new HealthMetricsRecord(userId);
                healthMetricsRepository.save(metricsRecord);
                cacheStore.put(userId, metricsRecord);
            }
        }
        return metricsRecord;
    }

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
                    metricsRecord.setTotalCalorieIntake(metricsRecord.getTotalCalorieIntake() + mealData.getCalorie());
                    metricsRecord.setCarbs(metricsRecord.getCarbs() + mealData.getCarb());
                    metricsRecord.setFats(metricsRecord.getFats() + mealData.getFat());
                    metricsRecord.setProtein(metricsRecord.getProtein() + mealData.getProtein());
                }
            }
        }


        double weightInKg = request.getWeightUnit().equals(WeightUnit.LBS)
                ? convertPoundsToKilograms(request.getWeight())
                : request.getWeight();

        if () {

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
}
