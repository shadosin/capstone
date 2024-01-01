package com.kenzie.appserver.config;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    // Create a Cache here if needed

    @Bean
    public CacheStore<String, HealthMetricsRecord> myCache() {
        Cache<String, HealthMetricsRecord> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(6, TimeUnit.HOURS)
                .build();
        return new CacheStore<>(cache);
    }

}
