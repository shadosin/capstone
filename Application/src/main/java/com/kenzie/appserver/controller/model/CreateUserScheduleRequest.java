package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    this.scheduledEvents = (scheduledEvents != null) ? scheduledEvents : new ArrayList<>();
    System.out.println(this.scheduledEvents);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
