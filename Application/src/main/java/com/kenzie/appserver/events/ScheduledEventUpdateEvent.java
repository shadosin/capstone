package com.kenzie.appserver.events;

import com.kenzie.appserver.service.model.ScheduledEvent;
import org.springframework.context.ApplicationEvent;

public class ScheduledEventUpdateEvent extends ApplicationEvent {
    private final String userId;
    private final ScheduledEvent scheduledEvent;

    public ScheduledEventUpdateEvent(Object source, String userId, ScheduledEvent scheduledEvent) {
        super(source);
        this.userId = userId;
        this.scheduledEvent = scheduledEvent;
    }

    public String getUserId() {
        return userId;
    }

    public ScheduledEvent getScheduledEvent() {
        return scheduledEvent;
    }
}
