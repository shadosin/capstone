package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.EventType;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;

public class ScheduledEventUpdateRequest {

    @NotEmpty
    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("mealId")
    private String mealId;
    @JsonProperty("exerciseId")
    private String exerciseId;
    @JsonProperty("eventType")
    private EventType eventType;
    @JsonProperty("scheduledDateTime")
    private ZonedDateTime scheduledDateTime;
    @JsonProperty("completed")
    private Boolean completed;
    @JsonProperty("metricsCompleted")
    private Boolean metricsCalculated;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isMetricsCalculated() {
        return metricsCalculated;
    }

    public void setMetricsCalculated(Boolean metricsCalculated) {
        this.metricsCalculated = metricsCalculated;
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
}
