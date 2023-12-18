package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class CreateUserScheduleRequest {
    @JsonProperty("start")
    private ZonedDateTime start;
    @JsonProperty("end")
    private ZonedDateTime end;
    @JsonProperty("scheduledItems")
    private List<String> scheduledItems;



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

    public List<String> getScheduleItems() {
        return scheduledItems;
    }

    public void setScheduleItems(List<String> scheduleItems) {
        this.scheduledItems = scheduleItems;
    }
}
