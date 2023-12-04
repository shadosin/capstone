package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface HealthMetricsRepository extends CrudRepository<HealthMetricsRecord, String> {
}
