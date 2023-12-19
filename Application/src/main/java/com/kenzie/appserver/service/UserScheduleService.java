package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserService userService;

    @Autowired
    public UserScheduleService(UserScheduleRepository userScheduleRepository, UserService userService) {
        this.userScheduleRepository = userScheduleRepository;
        this.userService = userService;
    }

    public UserScheduleRecord findById(String id) {
        return userScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No record found for ID: " + id));
    }

    public UserScheduleRecord findCurrentSchedule(String userId) {
        UserRecord userRecord = userService.findById(userId);
        if (userRecord == null) {
            throw new IllegalArgumentException("User does not exist with ID: " + userId);
        }
        List<String> userSchedules = userRecord.
    }
}
