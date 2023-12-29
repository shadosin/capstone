package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ScheduledEvent {

    private String eventId;
    private String userId;
    private String mealId;
    private String exerciseId;
    private EventType eventType;
    private ZonedDateTime scheduledDateTime;
    private boolean completed;
    private boolean metricsCalculated;

    public ScheduledEvent() {}

    public ScheduledEvent(String eventId,
                          String userId,
                          String mealId,
                          String exerciseId,
                          EventType eventType,
                          ZonedDateTime scheduledDateTime,
                          boolean completed,
                          boolean metricsCalculated) {
        this.eventId = eventId;
        this.userId = userId;
        this.mealId = mealId;
        this.exerciseId = exerciseId;
        this.eventType = eventType;
        this.scheduledDateTime = scheduledDateTime;
        this.completed = completed;
        this.metricsCalculated = metricsCalculated;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public ZonedDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(ZonedDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isMetricsCalculated() {
        return metricsCalculated;
    }

    public void setMetricsCalculated(boolean metricsCalculated) {
        this.metricsCalculated = metricsCalculated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledEvent that = (ScheduledEvent) o;
        return completed == that.completed &&
                metricsCalculated == that.metricsCalculated &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(mealId, that.mealId) &&
                Objects.equals(exerciseId, that.exerciseId) &&
                eventType == that.eventType &&
                Objects.equals(scheduledDateTime, that.scheduledDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId, mealId, exerciseId, eventType, scheduledDateTime, completed, metricsCalculated);
    }
}
