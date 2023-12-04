package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.MealRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MealRepository extends CrudRepository<MealRecord, String> {
}
