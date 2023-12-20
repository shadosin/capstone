package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.utils.UserConverter;
import com.kenzie.appserver.utils.UserScheduleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserService userService;
    private final ScheduledEventService scheduledEventService;

    @Autowired
    public UserScheduleService(UserScheduleRepository userScheduleRepository,
                               UserService userService,
                               ScheduledEventService scheduledEventService) {
        this.userScheduleRepository = userScheduleRepository;
        this.userService = userService;
        this.scheduledEventService = scheduledEventService;
    }

    public UserScheduleResponse findById(String scheduleId) {
        return new UserScheduleResponse(userScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("No record found for ID: " + scheduleId)));
    }

    public UserScheduleResponse createUserSchedule(CreateUserScheduleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Unable to create Schedule, as request is null");
        }
        UserRecord userRecord = userService.findById(request.getUserId());
        manageUserScheduleLimit(userRecord);

        UserScheduleRecord newSchedule = new UserScheduleRecord();
        newSchedule.setStart(request.getStart());
        newSchedule.setScheduledEventIds(new ArrayList<>());

        for (CreateScheduledEventRequest eventRequest : request.getScheduledEvents()) {
            ScheduledEventResponse scheduledEvent = scheduledEventService.createScheduledEvent(eventRequest);
            newSchedule.getScheduledEventIds().add(scheduledEvent.getEventId());
        }

        newSchedule = userScheduleRepository.save(newSchedule);
        userRecord.getUserScheduleIds().add(newSchedule.getScheduleId());
        userService.updateUser(userRecord.getUserId(), UserConverter.recordToUpdateUserRequest(userRecord));

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

        userService.updateUser(userRecord.get().getUserId(), UserConverter.recordToUpdateUserRequest(userRecord.get()));
        userScheduleRepository.deleteById(scheduleId);
    }

    public UserScheduleResponse updateUserSchedule(String scheduleId, UserScheduleUpdateRequest request) {
        UserScheduleRecord oldRecord = userScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with ID: " + scheduleId));

        if (request.getStart() != null) {
            oldRecord.setStart(request.getStart());
        }
        if (request.getEnd() != null) {
            oldRecord.setEnd(request.getEnd());
        }
        for (ScheduledEventUpdateRequest eventUpdateRequest : request.getScheduledEventUpdates()) {
            scheduledEventService.updateScheduledEvent(eventUpdateRequest.getEventId(), eventUpdateRequest);
        }

        UserScheduleRecord updatedRecord = userScheduleRepository.save(oldRecord);
        return new UserScheduleResponse(updatedRecord);
    }

    private void manageUserScheduleLimit(UserRecord record) {
        List<String> userScheduleIds = record.getUserScheduleIds();
        if (userScheduleIds.size() == 12) {
            String oldestScheduleId = userScheduleIds.stream()
                    .map(this::findById)
                    .map(UserScheduleConverter::convertToRecordFromResponse)
                    .sorted(Comparator.comparing(UserScheduleRecord::getStart))
                    .map(UserScheduleRecord::getScheduleId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Failed to find the oldest schedule for user with ID: " + record.getUserId()));

            userScheduleIds.remove(oldestScheduleId);
            userScheduleRepository.deleteById(oldestScheduleId);
        }
    }
}
