package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

public class CreateUserScheduleRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotEmpty
    @JsonProperty("start")
    private ZonedDateTime start;

    @NotEmpty
    @JsonProperty("end")
    private ZonedDateTime end;

    @JsonProperty("scheduledEventIds")
    private List<CreateScheduledEventRequest> scheduledEvents;



    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public List<CreateScheduledEventRequest> getScheduledEvents() {
        return scheduledEvents;
    }

    public void setScheduledEvents(List<CreateScheduledEventRequest> scheduledEvents) {
        this.scheduledEvents = scheduledEvents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
