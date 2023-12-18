package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.ScheduledEvent;
import org.joda.time.DateTime;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "UserSchedule")
public class UserScheduleRecord {
    private String scheduleId;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private List<String> scheduledItems;
    @DynamoDBHashKey(attributeName = "scheduleId")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    @DynamoDBAttribute(attributeName = "start")
    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }
    @DynamoDBAttribute(attributeName = "end")
    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
    @DynamoDBAttribute(attributeName = "scheduledItems")
    public List<String> getScheduleItems() {
        return scheduledItems;
    }

    public void setScheduleItems(List<String> scheduleItems) {
        this.scheduledItems = scheduleItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserScheduleRecord)) return false;
        UserScheduleRecord that = (UserScheduleRecord) o;
        return getScheduleId().equals(that.getScheduleId()) && getStart().equals(that.getStart()) && getEnd().equals(that.getEnd()) && scheduledItems.equals(that.scheduledItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScheduleId(), getStart(), getEnd(), scheduledItems);
    }
}
