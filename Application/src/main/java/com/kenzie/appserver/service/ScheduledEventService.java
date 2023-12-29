package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.controller.model.ScheduledEventResponse;
import com.kenzie.appserver.controller.model.ScheduledEventUpdateRequest;
import com.kenzie.appserver.repositories.ScheduledEventRepository;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.service.model.EventType;
import com.kenzie.appserver.service.model.ScheduledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduledEventService {

    private final ScheduledEventRepository scheduledEventRepository;
    private final HealthMetricsService healthMetricsService;


    @Autowired
    public ScheduledEventService(ScheduledEventRepository scheduledEventRepository,
                                 HealthMetricsService healthMetricsService) {
        this.scheduledEventRepository = scheduledEventRepository;
        this.healthMetricsService = healthMetricsService;
    }

    public ScheduledEventResponse findById(String eventId) {
        Optional<ScheduledEventRecord> record = scheduledEventRepository.findById(eventId);
        if (record.isEmpty()) {
            throw new IllegalArgumentException("Event does not exist with given ID:" + eventId);
        }
        return new ScheduledEventResponse(record.get());
    }

    public ScheduledEventResponse createScheduledEvent(CreateScheduledEventRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request was null");
        }
        ScheduledEventRecord record = new ScheduledEventRecord();
        record.setEventId(UUID.randomUUID().toString());
        record.setUserId(request.getUserId());
        record.setExerciseId(request.getExerciseId());
        record.setMealId(request.getMealId());
        record.setEventType(request.getEventType());
        record.setScheduledDateTime(request.getScheduledDateTime());
        record.setCompleted(false);
        record.setMetricsCalculated(false);

        record = scheduledEventRepository.save(record);

        return new ScheduledEventResponse(record);
    }

    public ScheduledEventResponse updateScheduledEvent(String eventId, ScheduledEventUpdateRequest request) {
        ScheduledEventResponse response = this.findById(eventId);
        ScheduledEvent event = eventFromResponse(response);

        if (request.getUserId() != null) {
            event.setUserId(request.getUserId());
        }
        if (request.getEventType() != null) {
            event.setEventType(request.getEventType());
        }
        if (request.getScheduledDateTime() != null) {
            event.setScheduledDateTime(request.getScheduledDateTime());
        }
        if (request.getExerciseId() != null && request.getEventType().equals(EventType.EXERCISE)) {
            event.setExerciseId(request.getExerciseId());
        }
        if (request.getMealId() != null && request.getEventType().equals(EventType.MEAL)) {
            event.setMealId(request.getMealId());
        }
        event.setCompleted(request.isCompleted());
        event.setMetricsCalculated(request.isMetricsCalculated());

        if (event.isCompleted() && !event.isMetricsCalculated()) {
            String userId = event.getUserId();
            healthMetricsService.updateMetricsBasedOnEvent(userId, event);
            event.setMetricsCalculated(true);
        }

        ScheduledEventRecord updatedRecord = scheduledEventRepository.save(recordFromScheduledEvent(event));

        return new ScheduledEventResponse(updatedRecord);
    }

    public void deleteScheduledEvent(String eventId) {
        Optional<ScheduledEventRecord> record = scheduledEventRepository.findById(eventId);
        if (record.isEmpty()) {
            throw new IllegalArgumentException("Event does not exist with given ID: " + eventId);
        }
        scheduledEventRepository.deleteById(eventId);
    }

    public List<ScheduledEventResponse> getAllScheduledEvents() {
        List<ScheduledEventRecord> records = new ArrayList<>();
        scheduledEventRepository.findAll().forEach(records::add);

        return records.stream()
                .map(ScheduledEventResponse::new)
                .collect(Collectors.toList());
    }

    private ScheduledEvent eventFromResponse(ScheduledEventResponse response) {
        ScheduledEvent event = new ScheduledEvent();
        event.setEventId(response.getEventId());
        event.setUserId(response.getUserId());
        event.setMealId(response.getMealId());
        event.setExerciseId(response.getExerciseId());
        event.setEventType(response.getEventType());
        event.setScheduledDateTime(response.getScheduledDateTime());
        event.setCompleted(response.isCompleted());
        event.setMetricsCalculated(response.isMetricsCalculated());
        return event;
    }

    private ScheduledEventRecord recordFromScheduledEvent(ScheduledEvent event) {
        ScheduledEventRecord record = new ScheduledEventRecord();
        record.setEventId(event.getEventId());
        record.setUserId(event.getUserId());
        record.setMealId(event.getMealId());
        record.setExerciseId(event.getExerciseId());
        record.setEventType(event.getEventType());
        record.setScheduledDateTime(event.getScheduledDateTime());
        record.setCompleted(event.isCompleted());
        record.setMetricsCalculated(event.isMetricsCalculated());
        return record;
    }
}

