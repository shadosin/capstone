package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ScheduledEvent {
    private ZonedDateTime scheduledDateTime;
    private String eventType;
    private String eventId;
    private boolean completed;
    private boolean metricsCalculated;

    public ScheduledEvent() {}

    public ScheduledEvent(ZonedDateTime scheduledDateTime,
                          String eventType,
                          String eventId,
                          boolean completed,
                          boolean metricsCalculated) {
        this.scheduledDateTime = scheduledDateTime;
        this.eventType = eventType;
        this.eventId = eventId;
        this.completed = completed;
        this.metricsCalculated = metricsCalculated;
    }

    public ZonedDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(ZonedDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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
                Objects.equals(scheduledDateTime, that.scheduledDateTime) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduledDateTime, eventType, eventId, completed, metricsCalculated);
    }
}
