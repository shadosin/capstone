package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.HealthMetricsRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import org.junit.jupiter.api.BeforeEach;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.HealthMetricsRepository;
import com.kenzie.appserver.repositories.ScheduledEventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.service.*;
import com.kenzie.appserver.service.model.*;
import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import com.kenzie.capstone.service.model.ExerciseData;
import com.kenzie.capstone.service.model.MealData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class HealthMetricsServiceTest {



        private HealthMetricsRepository healthMetricsRepository;
        private MealLambdaServiceClient mealLambdaServiceClient;
        private ExerciseLambdaServiceClient exerciseLambdaServiceClient;
        private UserRepository userRepository;
        private LambdaServiceClient lambdaServiceClient;
        private UserService userService;
        private UserScheduleService userScheduleService;
        private ScheduledEventService scheduledEventService;
        private CacheStore<String, HealthMetricsRecord> cacheStore;
        private HealthMetricsService healthMetricsService;
        private UserScheduleRepository userScheduleRepository;
        private ScheduledEventRepository scheduledEventRepository;

        @BeforeEach
        public void setup(){
            healthMetricsRepository = mock(HealthMetricsRepository.class);
            mealLambdaServiceClient = mock(MealLambdaServiceClient.class);
            exerciseLambdaServiceClient = mock(ExerciseLambdaServiceClient.class);
            userRepository = mock(UserRepository.class);
            lambdaServiceClient = mock(LambdaServiceClient.class);
            cacheStore = mock(CacheStore.class);
            userScheduleRepository = mock(UserScheduleRepository.class);
            scheduledEventRepository = mock(ScheduledEventRepository.class);

            userService = new UserService(userRepository);
            userScheduleService = new UserScheduleService(userScheduleRepository, userService, scheduledEventService);
            scheduledEventService = new ScheduledEventService(scheduledEventRepository, healthMetricsService);
            healthMetricsService = new HealthMetricsService(healthMetricsRepository, mealLambdaServiceClient,
                    exerciseLambdaServiceClient, userScheduleService, scheduledEventService, cacheStore);
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


}
