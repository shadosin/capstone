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

//    @PostMapping
//    public ResponseEntity<ScheduledEventResponse> createScheduledEvent(@RequestBody CreateScheduledEventRequest request) {
//
//    }

//    @PutMapping("/{eventId}")
//    public ResponseEntity<ScheduledEventResponse> updateScheduledEvent(@PathVariable("eventId") String eventId, @RequestBody ScheduledEventUpdateRequest request) {
//
//    }

//    @GetMapping("/{eventId}/")
//    public ResponseEntity<ScheduledEventResponse> getScheduledEvent(@PathVariable("eventId") String eventId) {
//
//    }

//    @DeleteMapping("/{eventId}")
//    public ResponseEntity<ScheduledEventResponse> deleteEvent(@PathVariable("eventId") String eventId) {
//
//    }

//    @GetMapping("/all")
//    public ResponseEntity<List<ScheduledEventResponse>> getAllScheduledEvents() {
//
//    }
}
