package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.controller.model.ScheduledEventResponse;
import com.kenzie.appserver.controller.model.ScheduledEventUpdateRequest;
import com.kenzie.appserver.repositories.ScheduledEventRepository;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.service.model.EventType;
import com.kenzie.appserver.utils.ScheduledEventConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduledEventService {

    private final ScheduledEventRepository scheduledEventRepository;


    @Autowired
    public ScheduledEventService(ScheduledEventRepository scheduledEventRepository) {
        this.scheduledEventRepository = scheduledEventRepository;
    }

    public ScheduledEventResponse findById(String eventId) {
        Optional<ScheduledEventRecord> record = scheduledEventRepository.findById(eventId);
        if (record.isEmpty()) {
            throw new IllegalArgumentException("Event does not exist with given ID");
        }
        return new ScheduledEventResponse(record.get());
    }

    public ScheduledEventResponse createScheduledEvent(CreateScheduledEventRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request was null");
        }
        ScheduledEventRecord record = ScheduledEventConverter.createRequestToRecord(request);
        record = scheduledEventRepository.save(record);

        return new ScheduledEventResponse(record);
    }

    public ScheduledEventResponse updateScheduledEvent(String eventId, ScheduledEventUpdateRequest request) {
        ScheduledEventRecord oldRecord = scheduledEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event does not exist with given ID:" + eventId));

        if (request.getEventType() != null) {
            oldRecord.setEventType(request.getEventType());
        }
        if (request.getScheduledDateTime() != null) {
            oldRecord.setScheduledDateTime(request.getScheduledDateTime());
        }
        if (request.getExerciseId() != null && request.getEventType().equals(EventType.EXERCISE)) {
            oldRecord.setExerciseId(request.getExerciseId());
        }
        if (request.getMealId() != null && request.getEventType().equals(EventType.MEAL)) {
            oldRecord.setMealId(request.getMealId());
        }
        if (request.isCompleted() != null) {
            oldRecord.setCompleted(request.isCompleted());
        }
        if (request.isMetricsCalculated() != null) {
            oldRecord.setMetricsCalculated(request.isMetricsCalculated());
        }

        ScheduledEventRecord updatedRecord = scheduledEventRepository.save(oldRecord);

        return new ScheduledEventResponse(updatedRecord);
    }

    public void deleteScheduledEvent(String eventId) {
        Optional<ScheduledEventRecord> record = scheduledEventRepository.findById(eventId);
        if (record.isEmpty()) {
            throw new IllegalArgumentException("Event does not exist with given ID");
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
}

