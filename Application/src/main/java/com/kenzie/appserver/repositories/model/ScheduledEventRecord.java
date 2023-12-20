package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
import com.kenzie.appserver.service.model.EventType;

import java.time.ZonedDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "ScheduledEvents")
public class ScheduledEventRecord {

    private String eventId;
    private String mealId;
    private String exerciseId;
    private EventType eventType;
    private ZonedDateTime scheduledDateTime;
    private boolean completed;
    private boolean metricsCalculated;

    @DynamoDBHashKey(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    @DynamoDBAttribute(attributeName = "mealId")
    public String getMealId() {return mealId;}

    @DynamoDBAttribute(attributeName = "exerciseId")
    public String getExerciseId() {return exerciseId;}

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    public EventType getEventType() {
        return eventType;
    }

    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "scheduledDateTime")
    public ZonedDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    @DynamoDBAttribute(attributeName = "completed")
    public boolean isCompleted() {
        return completed;
    }

    @DynamoDBAttribute(attributeName = "metricsCalculated")
    public boolean isMetricsCalculated() {
        return metricsCalculated;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setScheduledDateTime(ZonedDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setMetricsCalculated(boolean metricsCalculated) {
        this.metricsCalculated = metricsCalculated;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledEventRecord that = (ScheduledEventRecord) o;
        return completed == that.completed &&
                metricsCalculated == that.metricsCalculated &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(mealId, that.mealId) &&
                Objects.equals(exerciseId, that.exerciseId) &&
                eventType == that.eventType &&
                Objects.equals(scheduledDateTime, that.scheduledDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, mealId, exerciseId, eventType, scheduledDateTime, completed, metricsCalculated);
    }
}
