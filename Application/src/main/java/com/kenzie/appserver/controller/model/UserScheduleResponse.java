package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserScheduleResponse {
    @JsonProperty("scheduleId")
    private String scheduleId;
    @JsonProperty("start")
    private ZonedDateTime start;
    @JsonProperty("end")
    private ZonedDateTime end;
    @JsonProperty("scheduledItems")
    private List<String> scheduledItems;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

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
    public List<String> getScheduledItems() {
        return scheduledItems;
    }

    public void setScheduledItems(List<String> scheduledItems) {
        this.scheduledItems = scheduledItems;
    }


}
