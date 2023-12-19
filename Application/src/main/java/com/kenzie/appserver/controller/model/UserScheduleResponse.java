package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;

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
    @JsonProperty("scheduledEventIds")
    private List<String> scheduledEventIds;
    @JsonProperty("userId")
    private String userId;

    public UserScheduleResponse(){}

    public UserScheduleResponse (UserScheduleRecord record){
        this.scheduleId = record.getScheduleId();
        this.start = record.getStart();
        this.end = record.getEnd();
        this.scheduledEventIds = record.getScheduledEventIds();
        this.userId = record.getUserId();
    }

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
