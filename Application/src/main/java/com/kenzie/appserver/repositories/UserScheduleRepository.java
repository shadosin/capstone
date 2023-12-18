package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import org.springframework.data.repository.CrudRepository;

public interface UserScheduleRepository extends CrudRepository<UserScheduleRecord, String> {
}
