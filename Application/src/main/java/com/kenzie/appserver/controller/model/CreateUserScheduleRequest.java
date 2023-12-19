package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class CreateUserScheduleRequest {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("start")
    private ZonedDateTime start;
    @JsonProperty("end")
    private ZonedDateTime end;
    @JsonProperty("scheduledEventIds")
    private List<String> scheduledEventIds;



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

    public List<String> getScheduledEventIds() {
        return scheduledEventIds;
    }

    public void setScheduledEventIds(List<String> scheduledEventIds) {
        this.scheduledEventIds = scheduledEventIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
