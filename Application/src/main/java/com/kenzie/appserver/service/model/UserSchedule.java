package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class UserSchedule {

    private String scheduleId;
    private String userId;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private List<String> scheduledEventIds;

    public UserSchedule() {}

    public UserSchedule(String scheduleId,
                        String userId,
                        ZonedDateTime start,
                        ZonedDateTime end,
                        List<String> scheduledEventIds) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.scheduledEventIds = scheduledEventIds;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSchedule that = (UserSchedule) o;
        return Objects.equals(scheduleId, that.scheduleId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(scheduledEventIds, that.scheduledEventIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, userId, start, end, scheduledEventIds);
    }
}
