package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ScheduledEventRepository extends CrudRepository<ScheduledEventRecord, String> {
}
