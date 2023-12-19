package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "UserSchedule")
public class UserScheduleRecord {
    private String scheduleId;
    private String userId;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private List<String> scheduledEventIds;

    @DynamoDBHashKey(attributeName = "scheduleId")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "start")
    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "end")
    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
    @DynamoDBAttribute(attributeName = "scheduledEventIds")
    public List<String> getScheduledEventIds() {
        return scheduledEventIds;
    }

    public void setScheduledEventIds(List<String> scheduledEventIds) {
        this.scheduledEventIds = scheduledEventIds;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserScheduleRecord)) return false;
        UserScheduleRecord that = (UserScheduleRecord) o;
        return getScheduleId().equals(that.getScheduleId()) &&
                getStart().equals(that.getStart()) &&
                getEnd().equals(that.getEnd()) &&
                getScheduledEventIds().equals(that.getScheduledEventIds()) &&
                getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScheduleId(), getStart(), getEnd(), getScheduledEventIds(), getUserId());
    }
}
