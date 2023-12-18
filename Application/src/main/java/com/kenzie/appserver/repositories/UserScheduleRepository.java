package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserScheduleRepository extends CrudRepository<UserScheduleRecord, String> {
}
