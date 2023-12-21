package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.controller.model.ScheduledEventResponse;
import com.kenzie.appserver.controller.model.ScheduledEventUpdateRequest;
import com.kenzie.appserver.service.ScheduledEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/events")
public class ScheduledEventController {

    private final ScheduledEventService scheduledEventService;

    @Autowired
    public ScheduledEventController(ScheduledEventService scheduledEventService) {
        this.scheduledEventService = scheduledEventService;
    }

    @PostMapping
    public ResponseEntity<ScheduledEventResponse> createScheduledEvent(@RequestBody CreateScheduledEventRequest request) {
        ScheduledEventResponse response = scheduledEventService.createScheduledEvent(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ScheduledEventResponse> updateScheduledEvent(@PathVariable("eventId") String eventId,
                                                                       @RequestBody ScheduledEventUpdateRequest request) {
        ScheduledEventResponse response = scheduledEventService.updateScheduledEvent(eventId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ScheduledEventResponse> getScheduledEventById(@PathVariable("eventId") String eventId) {
        ScheduledEventResponse response = scheduledEventService.findById(eventId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") String eventId) {
        scheduledEventService.deleteScheduledEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduledEventResponse>> getAllScheduledEvents() {
        List<ScheduledEventResponse> responses = scheduledEventService.getAllScheduledEvents();
        return ResponseEntity.ok(responses);
    }

}
