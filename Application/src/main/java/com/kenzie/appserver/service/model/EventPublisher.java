package com.kenzie.appserver.service.model;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishScheduledEventUpdate(String userId, ScheduledEvent scheduledEvent) {
        ScheduledEventUpdateEvent event = new ScheduledEventUpdateEvent(this, userId, scheduledEvent);
        publisher.publishEvent(event);
    }
}
