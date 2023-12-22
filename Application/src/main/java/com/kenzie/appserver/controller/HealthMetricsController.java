package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.HealthMetricsResponse;
import com.kenzie.appserver.controller.model.HealthMetricsUpdateRequest;
import com.kenzie.appserver.repositories.model.HealthMetricsRecord;
import com.kenzie.appserver.service.HealthMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/healthMetrics")
public class HealthMetricsController {

    private final HealthMetricsService healthMetricsService;

    @Autowired
    public HealthMetricsController(HealthMetricsService healthMetricsService) {
        this.healthMetricsService = healthMetricsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<HealthMetricsResponse> getHealthMetrics(@PathVariable String userId) {
        HealthMetricsRecord metricsRecord = healthMetricsService.getHealthMetrics(userId);
        return ResponseEntity.ok(new HealthMetricsResponse(metricsRecord));
    }

    @PostMapping("/update")
    public ResponseEntity<HealthMetricsResponse> updateHealthMetrics(@RequestBody HealthMetricsUpdateRequest request) {
        HealthMetricsResponse response = healthMetricsService.updateHealthMetrics(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteHealthMetrics(@PathVariable String userId) {
        healthMetricsService.deleteHealthMetrics(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset/{userId}")
    public ResponseEntity<Void> resetHealthMetrics(@PathVariable String userId) {
        healthMetricsService.resetHealthMetrics(userId);
        return ResponseEntity.ok().build();
    }
}
