package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExerciseEventDataResponse;
import com.kenzie.appserver.controller.model.MealEventDataResponse;
import com.kenzie.appserver.service.EventDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eventData")
public class EventDataController {

    private final EventDataService eventDataService;

    public EventDataController(EventDataService eventDataService) {
        this.eventDataService = eventDataService;
    }

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<MealEventDataResponse> getMealEventData(@PathVariable("mealId") String mealId) {
        MealEventDataResponse response = eventDataService.getMealDataById(mealId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meal/{type}")
    public ResponseEntity<List<MealEventDataResponse>> getMealEventDataByType(@PathVariable("type") String type) {
        List<MealEventDataResponse> responses = eventDataService.getMealDataByType(type);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<ExerciseEventDataResponse> getExerciseEventData(@PathVariable("exerciseId") String exerciseId) {
        ExerciseEventDataResponse response = eventDataService.getExerciseDataById(exerciseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exercise/{type}")
    public ResponseEntity<List<ExerciseEventDataResponse>> getExerciseEventDataByType(@PathVariable("type") String type) {
        List<ExerciseEventDataResponse> responses = eventDataService.getExerciseDataByType(type);
        return ResponseEntity.ok(responses);
    }
}
