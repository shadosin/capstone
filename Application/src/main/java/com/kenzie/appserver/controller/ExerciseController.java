package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExerciseCreateRequest;
import com.kenzie.appserver.controller.model.ExerciseResponse;
import com.kenzie.appserver.controller.model.ExerciseUpdateRequest;
import com.kenzie.appserver.service.ExerciseService;
import com.kenzie.appserver.service.model.Exercise;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private ExerciseService service;
    ExerciseController(ExerciseService service){
        this.service = service;
    }

    @GetMapping("/exerciseId")
    public ResponseEntity<ExerciseResponse> getExercise(@PathVariable("exerciseId") String exerciseId){
        Exercise exercise = service.findById(exerciseId);
        if(exercise == null){
            return ResponseEntity.notFound().build();
        }
        ExerciseResponse response = createResponse(exercise);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercises(){
        List<Exercise> exercises = service.findAllExercises();
        if(exercises == null || exercises.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ExerciseResponse> responses = new ArrayList<>();
        for(Exercise exercise: exercises){
            responses.add(createResponse(exercise));
        }
        return ResponseEntity.ok(responses);
    }
    @PutMapping
    public ResponseEntity<ExerciseResponse> updateExercise(@RequestBody ExerciseUpdateRequest exerciseUpdateRequest){
        Exercise exercise = new Exercise(exerciseUpdateRequest.getExerciseId(), exerciseUpdateRequest.getType(),
                exerciseUpdateRequest.getIntensity(),exerciseUpdateRequest.getExerciseName(), exerciseUpdateRequest.getDuration(),
                exerciseUpdateRequest.getReps(), exerciseUpdateRequest.getSets(), exerciseUpdateRequest.getDistance(), exerciseUpdateRequest.getMETS(),
                exerciseUpdateRequest.getDescription());
        service.updateExercise(exercise);
        ExerciseResponse response = createResponse(exercise);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<ExerciseResponse> addNewExercise(@RequestBody ExerciseCreateRequest exercise){
        Exercise builder = new Exercise(exercise.getExerciseId(), exercise.getType(), exercise.getIntensity()
                ,exercise.getExerciseName(), exercise.getDuration(), exercise.getReps(), exercise.getSets(), exercise.getDistance(), exercise.getMETS(),
                exercise.getDescription());
        service.addNewExercise(builder);
        ExerciseResponse response = createResponse(builder);
        return ResponseEntity.created(URI.create("/exercise/" + response.getExerciseName())).body(response);
    }
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponse> deleteExerciseById(@PathVariable("exerciseId") String exerciseId){
        service.deleteExercise(exerciseId);
        return ResponseEntity.noContent().build();
    }

    private ExerciseResponse createResponse(Exercise exercise){
        ExerciseResponse response = new ExerciseResponse();
        response.setExerciseId(exercise.getExerciseId());
        response.setType(exercise.getType());
        response.setIntensity(exercise.getIntensity());
        response.setExerciseName(exercise.getExerciseName());
        response.setDuration(exercise.getDuration());
        response.setReps(exercise.getReps());
        response.setSets(exercise.getSets());
        response.setDistance(exercise.getDistance());
        response.setMETS(exercise.getMETS());
        response.setDescription(exercise.getDescription());
        return response;
    }
}
