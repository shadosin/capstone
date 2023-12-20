package com.kenzie.appserver.utils;

import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;

import java.util.UUID;

public class ScheduledEventConverter {

    public static ScheduledEventRecord createRequestToRecord(CreateScheduledEventRequest request) {
        ScheduledEventRecord record = new ScheduledEventRecord();
        record.setEventId(UUID.randomUUID().toString());
        record.setExerciseId(request.getExerciseId());
        record.setMealId(request.getMealId());
        record.setEventType(request.getEventType());
        record.setScheduledDateTime(request.getScheduledDateTime());
        record.setCompleted(false);
        record.setMetricsCalculated(false);

        return record;
    }

}
