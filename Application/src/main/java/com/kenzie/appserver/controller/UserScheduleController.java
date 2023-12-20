package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateUserScheduleRequest;
import com.kenzie.appserver.controller.model.UserScheduleResponse;
import com.kenzie.appserver.controller.model.UserScheduleUpdateRequest;
import com.kenzie.appserver.service.UserScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user-schedules")
public class UserScheduleController {

    private final UserScheduleService userScheduleService;

    @Autowired
    public UserScheduleController(UserScheduleService userScheduleService) {
        this.userScheduleService = userScheduleService;
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<UserScheduleResponse> findById(@PathVariable String scheduleId) {
        UserScheduleResponse response = userScheduleService.findById(scheduleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserScheduleResponse> createUserSchedule(@RequestBody CreateUserScheduleRequest request) {
        UserScheduleResponse response = userScheduleService.createUserSchedule(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current/{userId}")
    public ResponseEntity<UserScheduleResponse> findCurrentSchedule(@PathVariable String userId) {
        UserScheduleResponse response = userScheduleService.findCurrentSchedule(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteUserScheduleById(@PathVariable String scheduleId) {
        userScheduleService.deleteUserScheduleById(scheduleId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<UserScheduleResponse> updateUserSchedule(@PathVariable String scheduleId,
                                                                   @RequestBody UserScheduleUpdateRequest request) {
        UserScheduleResponse response = userScheduleService.updateUserSchedule(scheduleId, request);
        return ResponseEntity.ok(response);
    }
  
}
