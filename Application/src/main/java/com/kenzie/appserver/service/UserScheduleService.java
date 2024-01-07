package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.service.model.UserSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserService userService;
    private ScheduledEventService scheduledEventService;

    @Autowired
    public UserScheduleService(UserScheduleRepository userScheduleRepository,
                               UserService userService) {
        this.userScheduleRepository = userScheduleRepository;
        this.userService = userService;
    }

    @Autowired
    @Lazy
    public void setScheduledEventService(ScheduledEventService scheduledEventService) {
        this.scheduledEventService = scheduledEventService;
    }

    public UserScheduleResponse createUserSchedule(CreateUserScheduleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Unable to create Schedule, as request is null");
        }
        UserResponse userResponse = userService.findById(request.getUserId());
        User user = userService.userFromResponse(userResponse);

        UserScheduleRecord scheduleRecord = new UserScheduleRecord();
        scheduleRecord.setScheduleId(UUID.randomUUID().toString());
        scheduleRecord.setUserId(request.getUserId());
        scheduleRecord.setStart(request.getStart());
        scheduleRecord.setEnd(request.getEnd());

        List<ScheduledEventResponse> scheduledEvents = new ArrayList<>();
        if(request.getScheduledEvents() != null) {
            for (CreateScheduledEventRequest eventRequest : request.getScheduledEvents()) {
                ScheduledEventResponse scheduledEvent = scheduledEventService.createScheduledEvent(eventRequest);
                scheduledEvents.add(scheduledEvent);
            }
        }

        List<String> eventIds = scheduledEvents.stream()
                .map(ScheduledEventResponse::getEventId)
                .collect(Collectors.toList());
        scheduleRecord.setScheduledEventIds(eventIds);

        scheduleRecord = userScheduleRepository.save(scheduleRecord);

        user.getUserScheduleIds().add(scheduleRecord.getScheduleId());

        if (user.getUserScheduleIds().size() > 12) {
            manageUserScheduleLimit(user);
        }

        userService.updateUser(user.getUserId(), new UserUpdateRequest(user));

        return new UserScheduleResponse(scheduleRecord);
    }

    private void manageUserScheduleLimit(User user) {
        List<String> userScheduleIds = user.getUserScheduleIds();
        if (userScheduleIds.size() > 12) {
            String oldestScheduleId = userScheduleIds.stream()
                    .map(this::findById)
                    .sorted(Comparator.comparing(UserScheduleResponse::getStart))
                    .map(UserScheduleResponse::getScheduleId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Failed to find the oldest schedule for user with ID: " + user.getUserId()));

            userScheduleIds.remove(oldestScheduleId);
            userScheduleRepository.deleteById(oldestScheduleId);
        }
    }

    public UserScheduleResponse findById(String scheduleId) {
        return new UserScheduleResponse(userScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found for ID: " + scheduleId)));
    }

    public UserScheduleResponse findCurrentSchedule(String userId) {
        UserResponse userResponse = userService.findById(userId);
        List<String> userScheduleIds = userResponse.getUserScheduleIds();

        if (userScheduleIds == null || userScheduleIds.isEmpty()) {
            throw new IllegalArgumentException(String.format("User with Id: %s does not have a current schedule", userId));
        }

        ZonedDateTime now = ZonedDateTime.now();

        Optional<UserScheduleResponse> optionalResponse = userScheduleIds.stream()
                .map(this::findById)
                .filter(response -> response.getStart().isBefore(now))
                .filter(response -> response.getEnd().isAfter(now))
                .findFirst();

        if (optionalResponse.isEmpty()) {
            throw new IllegalArgumentException(String.format("User with Id: %s does not have a current schedule", userId));
        }
        return optionalResponse.get();
    }

    public void deleteUserScheduleById(String scheduleId) {
        UserScheduleResponse userScheduleResponse = this.findById(scheduleId);

        String userId = userScheduleResponse.getUserId();
        User user = userService.userFromResponse(userService.findById(userId));

        List<String> userScheduleIds = user.getUserScheduleIds();
        userScheduleIds.remove(scheduleId);

        userService.updateUser(userId, new UserUpdateRequest(user));
        userScheduleRepository.deleteById(scheduleId);
    }

    public UserScheduleResponse updateUserSchedule(String scheduleId, UserScheduleUpdateRequest request) {
        UserScheduleResponse response = this.findById(scheduleId);
        UserSchedule schedule = scheduleFromResponse(response);

        if (request.getScheduleId() != null) {
            schedule.setScheduleId(request.getScheduleId());
        }
        if (request.getUserId() != null) {
            schedule.setUserId(request.getUserId());
        }
        if (request.getStart() != null) {
            schedule.setStart(request.getStart());
        }
        if (request.getEnd() != null) {
            schedule.setEnd(request.getEnd());
        }
        for (ScheduledEventUpdateRequest eventUpdateRequest : request.getScheduledEventUpdates()) {
            scheduledEventService.updateScheduledEvent(eventUpdateRequest.getEventId(), eventUpdateRequest);
        }

        UserScheduleRecord updatedRecord = userScheduleRepository.save(recordFromUserSchedule(schedule));
        return new UserScheduleResponse(updatedRecord);
    }

    private UserSchedule scheduleFromResponse(UserScheduleResponse response) {
        UserSchedule schedule = new UserSchedule();
        schedule.setScheduleId(response.getScheduleId());
        schedule.setUserId(response.getUserId());
        schedule.setStart(response.getStart());
        schedule.setEnd(response.getEnd());
        schedule.setScheduledEventIds(response.getScheduledEventIds());
        return schedule;
    }

    private UserScheduleRecord recordFromUserSchedule(UserSchedule schedule) {
        UserScheduleRecord record = new UserScheduleRecord();
        record.setScheduleId(schedule.getScheduleId());
        record.setUserId(schedule.getUserId());
        record.setStart(schedule.getStart());
        record.setEnd(schedule.getEnd());
        record.setScheduledEventIds(schedule.getScheduledEventIds());
        return record;
    }

}
