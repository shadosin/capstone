package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ExerciseRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ExerciseRepository extends CrudRepository<ExerciseRecord, String> {

}
