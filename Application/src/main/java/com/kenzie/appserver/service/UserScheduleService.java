package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateUserScheduleRequest;
import com.kenzie.appserver.controller.model.UserScheduleResponse;
import com.kenzie.appserver.controller.model.UserScheduleUpdateRequest;
import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.utils.UserScheduleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserService userService;

    @Autowired
    public UserScheduleService(UserScheduleRepository userScheduleRepository, UserService userService) {
        this.userScheduleRepository = userScheduleRepository;
        this.userService = userService;
    }

    public UserScheduleResponse findById(String scheduleId) {
        return new UserScheduleResponse(userScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("No record found for ID: " + scheduleId)));
    }

    public UserScheduleResponse createUserSchedule(CreateUserScheduleRequest request) {
        Optional<CreateUserScheduleRequest> optionalRequest = Optional.ofNullable(request);

        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Unable to create Schedule, as request is null");
        }

        Optional<UserRecord> userRecord = Optional.ofNullable(userService.findById(optionalRequest.get().getUserId()));

        if (userRecord.isEmpty()) {
            throw new IllegalArgumentException("User does not exist with given ID: " + optionalRequest.get().getUserId());
        }

        List<String> userScheduleIds = userRecord.get().getUserScheduleIds();

        if (userScheduleIds.size() == 12) {
            String oldestScheduleId = userScheduleIds.stream()
                    .map(this::findById)
                    .map(UserScheduleConverter::convertToRecordFromResponse)
                    .sorted(Comparator.comparing(UserScheduleRecord::getStart))
                    .map(UserScheduleRecord::getScheduleId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Failed to find the oldest schedule for user with ID: " + userRecord.get().getUserId()));

            userScheduleIds.remove(oldestScheduleId);
            userScheduleRepository.deleteById(oldestScheduleId);
        }

        UserScheduleRecord newSchedule = UserScheduleConverter.convertToRecordFromRequest(request);
        userScheduleIds.add(newSchedule.getScheduleId());
        userService.updateUser(userRecord.get());

        return new UserScheduleResponse(newSchedule);
    }

    public UserScheduleResponse findCurrentSchedule(String userId) {
        UserRecord userRecord = userService.findById(userId);
        if (userRecord == null) {
            throw new IllegalArgumentException("User does not exist with ID: " + userId);
        }
        List<String> userScheduleIds = userRecord.getUserScheduleIds();

        ZonedDateTime now = ZonedDateTime.now();

        Optional<UserScheduleRecord> optionalRecord = userScheduleIds.stream()
                .map(this::findById)
                .map(UserScheduleConverter::convertToRecordFromResponse)
                .filter(record -> record.getStart().isBefore(now))
                .filter(record -> record.getEnd().isAfter(now))
                .findFirst();

        if (optionalRecord.isEmpty()) {
            throw new IllegalArgumentException(String.format("User with Id: %s does not have a current schedule", userId));
        }
        return new UserScheduleResponse(optionalRecord.get());
    }

    public void deleteUserScheduleById(String scheduleId) {
        Optional<UserScheduleRecord> scheduleRecord = userScheduleRepository.findById(scheduleId);
        if (scheduleRecord.isEmpty()) {
            throw new IllegalArgumentException("No record exists with given ID: " + scheduleId);
        }

        Optional<UserRecord> userRecord = Optional.ofNullable(userService.findById(scheduleRecord.get().getUserId()));
        if (userRecord.isEmpty()) {
            throw new IllegalArgumentException("No user exists with given user ID: " + scheduleRecord.get().getUserId());
        }

        List<String> userScheduleIds = userRecord.get().getUserScheduleIds();
        userScheduleIds.remove(scheduleId);

        userService.updateUser(userRecord.get());
        userScheduleRepository.deleteById(scheduleId);
    }

    public UserScheduleResponse updateUserSchedule(String scheduleId, UserScheduleUpdateRequest request) {
        Optional<UserScheduleRecord> oldRecord = userScheduleRepository.findById(scheduleId);
        if (oldRecord.isEmpty()) {
            throw new IllegalArgumentException("No schedule exists with given ID: " + scheduleId);
        }
        UserScheduleRecord updatedRecord = oldRecord.get();
        updatedRecord.setUserId(request.getUserId());
        updatedRecord.setScheduleId(request.getScheduleId());
        updatedRecord.setStart(request.getStart());
        updatedRecord.setEnd(request.getEnd());
        updatedRecord.setScheduledEventIds(request.getScheduledEventIds());

        userScheduleRepository.save(updatedRecord);

        return new UserScheduleResponse(updatedRecord);
    }
}
