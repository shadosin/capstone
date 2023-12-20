package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.ExerciseLambdaServiceClient;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.client.MealLambdaServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaServiceClientConfiguration {

    @Bean
    public LambdaServiceClient referralServiceClient() {
        return new LambdaServiceClient();
    }

    @Bean
    public MealLambdaServiceClient mealLambdaServiceClient() {
        return new MealLambdaServiceClient();
    }

    @Bean
    public ExerciseLambdaServiceClient exerciseLambdaServiceClient() {
        return new ExerciseLambdaServiceClient();
    }
}
